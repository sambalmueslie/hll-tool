package de.sambalmueslie.games.hll.tool.logic.server.monitor


import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.ServerService
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitoringProcessor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.db.ServerMonitorSettingsData
import de.sambalmueslie.games.hll.tool.logic.server.monitor.db.ServerMonitorSettingsRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.TaskScheduler
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import kotlin.system.measureTimeMillis

@Singleton
class ServerMonitorService(
    private val serverService: ServerService,
    private val repository: ServerMonitorSettingsRepository,
    private val processors: Set<ServerMonitoringProcessor>,
    @param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler
) : ApplicationEventListener<ServerStartupEvent> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ServerMonitorService::class.java)
        private val DELAY = Duration.ofSeconds(2)
    }

    private val clients = mutableMapOf<Long, ServerClient>()

    override fun onApplicationEvent(event: ServerStartupEvent) {
        serverService.getAll().forEachWithTryCatch { setupServer(it) }
        serverService.register(object : BusinessObjectChangeListener<Server> {
            override fun created(obj: Server) {
                repository.save(ServerMonitorSettingsData(obj.id, mapTrackingEnabled = true))
                setupServer(obj)
            }

            override fun updated(obj: Server) {
                // intentionally left empty
            }

            override fun deleted(obj: Server) {
                repository.deleteById(obj.id)
                clients.remove(obj.id)
            }
        })

        taskScheduler.scheduleWithFixedDelay(null, DELAY) {
            clients.values.forEachWithTryCatch { runServerCycle(it) }

        }
    }

    private fun runServerCycle(client: ServerClient) {
        val duration = measureTimeMillis { processors.forEach { it.runCycle(client) } }
        logger.trace("[${client.id}] Run server cycle ${client.name} within $duration ms.")
    }

    private fun setupServer(server: Server) {
        logger.info("[${server.id}] Setup Server ${server.name}")
        val connection = serverService.getConnection(server)
        if (connection == null) {
            logger.error("Cannot find server connection for ${server.name}")
            return
        }
        val settings = repository.findByIdOrNull(server.id)
        if (settings == null) {
            logger.error("Cannot find server settings for ${server.name}")
            return
        }
        val client = ServerClient(server, connection, settings)

        // TODO update name on server
//        val name = instance.name
//        if (name.isNotBlank() && data.name != name) {
//            data.name = name
//            repository.update(data)
//        }

        if (client.isEnabled()) {
            clients[server.id] = client
        } else {
            client.disconnect()
        }
    }


}
