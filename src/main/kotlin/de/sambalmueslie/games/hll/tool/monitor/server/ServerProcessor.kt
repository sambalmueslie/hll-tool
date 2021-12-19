package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerRepository
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
open class ServerProcessor(
    private val repository: ServerRepository,
    private val settingsRepository: ServerSettingsRepository,
    private val processors: Set<ServerInstanceProcessor>,
    @param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler
) : ApplicationEventListener<ServerStartupEvent> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerProcessor::class.java)
        private val DELAY = Duration.ofSeconds(2)
    }

    override fun onApplicationEvent(event: ServerStartupEvent) {
        repository.findAll().forEachWithTryCatch { setupServer(it) }

        taskScheduler.scheduleWithFixedDelay(null, DELAY) {
            instances.values.forEachWithTryCatch { runServerCycle(it) }
        }
    }

    private fun runServerCycle(instance: ServerInstance) {
        val duration = measureTimeMillis { processors.forEach { it.runCycle(instance) } }
        logger.debug("[${instance.id}] Run server cycle ${instance.name} within $duration ms.")
    }

    private val instances = mutableMapOf<Long, ServerInstance>()

    private fun setupServer(data: ServerData) {
        logger.info("[${data.id}] Setup Server ${data.name}")
        val settings = settingsRepository.findByIdOrNull(data.id) ?: return logger.error("Cannot find server settings for ${data.name}")
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
    }

    fun getInstanceByServerId(serverId: Long) = instances[serverId]

}
