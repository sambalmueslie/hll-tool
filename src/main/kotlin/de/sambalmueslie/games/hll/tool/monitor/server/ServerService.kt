package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.monitor.server.api.ServerChangeRequest
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerRepository
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
class ServerService(
    private val repository: ServerRepository,
    private val settingsRepository: ServerSettingsRepository,
    private val processors: Set<ServerInstanceProcessor>,
    @param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler
) : ApplicationEventListener<ServerStartupEvent> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerService::class.java)
        private val DELAY = Duration.ofSeconds(2)
    }

    override fun onApplicationEvent(event: ServerStartupEvent) {
        repository.findAll().forEachWithTryCatch { setupServer(it) }

        taskScheduler.scheduleWithFixedDelay(null, DELAY) {
            instances.values.forEachWithTryCatch { runServerCycle(it) }
        }
    }

    fun create(request: ServerChangeRequest): ServerInstance? {
        val existing = repository.findByHost(request.host)
        if (existing != null) return update(existing, request)

        val data = repository.save(ServerData.convert(request))
        settingsRepository.save(ServerSettingsData.convert(data, request.settings))
        return setupServer(data)
    }

    private fun update(data: ServerData, request: ServerChangeRequest): ServerInstance? {
        // TODO update server data
        return instances[data.id] ?: setupServer(data)
    }


    private fun runServerCycle(instance: ServerInstance) {
        val duration = measureTimeMillis { processors.forEach { it.runCycle(instance) } }
        logger.trace("[${instance.id}] Run server cycle ${instance.name} within $duration ms.")
    }

    private val instances = mutableMapOf<Long, ServerInstance>()

    private fun setupServer(data: ServerData): ServerInstance? {
        logger.info("[${data.id}] Setup Server ${data.name}")
        val settings = settingsRepository.findByIdOrNull(data.id)
        if (settings == null) {
            logger.error("Cannot find server settings for ${data.name}")
            return null
        }
        val instance = ServerInstance(data, settings)

        val name = instance.name
        if (name.isNotBlank() && data.name != name) {
            data.name = name
            repository.update(data)
        }

        if (instance.isEnabled()) {
            instances[data.id] = instance
        } else {
            instance.disconnect()
        }
        return instance
    }

    fun getInstanceByServerId(serverId: Long) = instances[serverId]

}
