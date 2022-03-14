package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.ServerService
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnection
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsRepository
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
class ServerUpdateService(
    private val serverService: ServerService,
    private val settingsRepository: ServerSettingsRepository,
    private val processors: Set<ServerInstanceProcessor>,
    @param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler
) : ApplicationEventListener<ServerStartupEvent> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerUpdateService::class.java)
        private val DELAY = Duration.ofSeconds(2)
    }

    override fun onApplicationEvent(event: ServerStartupEvent) {
        serverService.getAll().forEachWithTryCatch { setupServer(it) }
        serverService.register(object : BusinessObjectChangeListener<Server> {
            override fun created(obj: Server) {
                settingsRepository.save(ServerSettingsData(obj.id, mapTrackingEnabled = true))
                setupServer(obj)
            }

            override fun updated(obj: Server) {
                // intentionally left empty
            }

            override fun deleted(obj: Server) {
                settingsRepository.deleteById(obj.id)
                instances.remove(obj.id)
            }
        })

        taskScheduler.scheduleWithFixedDelay(null, DELAY) {
            instances.values.forEachWithTryCatch { runServerCycle(it) }

        }
    }

    private fun runServerCycle(instance: ServerInstance) {
        val duration = measureTimeMillis { processors.forEach { it.runCycle(instance) } }
        logger.trace("[${instance.id}] Run server cycle ${instance.name} within $duration ms.")
    }

    private val instances = mutableMapOf<Long, ServerInstance>()

    private fun setupServer(server: Server): ServerInstance? {
        logger.info("[${server.id}] Setup Server ${server.name}")
        val connection = serverService.getConnection(server)
        if (connection == null) {
            logger.error("Cannot find server connection for ${server.name}")
            return null
        }
        val settings = settingsRepository.findByIdOrNull(server.id)
        if (settings == null) {
            logger.error("Cannot find server settings for ${server.name}")
            return null
        }
        val instance = ServerInstance(server, connection, settings)

        // TODO update name on server
//        val name = instance.name
//        if (name.isNotBlank() && data.name != name) {
//            data.name = name
//            repository.update(data)
//        }

        if (instance.isEnabled()) {
            instances[server.id] = instance
        } else {
            instance.disconnect()
        }
        return instance
    }

    fun getInstanceByServerId(serverId: Long) = instances[serverId]

}
