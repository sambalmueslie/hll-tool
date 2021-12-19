package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerRepository
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class ServerProcessor(
    private val repository: ServerRepository,
    private val settingsRepository: ServerSettingsRepository,
    private val processors: Set<ServerInstanceProcessor>
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerProcessor::class.java)
    }

    private var initialized: Boolean = false

    @Scheduled(fixedDelay = "2s", initialDelay = "10s")
    open fun runPollJobs() {
        if (!initialized) initialize()

        _instances.values.forEachWithTryCatch { instance -> processors.forEach { it.runCycle(instance) } }
    }

    private fun initialize() {
        repository.findAll().forEachWithTryCatch { setupServer(it) }
        initialized = true
    }

    private val _instances = mutableMapOf<Long, ServerInstance>()

    private fun setupServer(data: ServerData) {
        val settings = settingsRepository.findByIdOrNull(data.id) ?: return logger.error("Cannot find server settings for ${data.name}")
        val instance = ServerInstance(data, settings)

        val name = instance.name
        if (name.isNotBlank() && data.name != name) {
            data.name = name
            repository.update(data)
        }

        if (instance.isEnabled()) {
            _instances[data.id] = instance
        } else {
            instance.disconnect()
        }
    }

    val instances: Collection<ServerInstance> = _instances.values
    fun getInstanceByServerId(serverId: Long) = _instances[serverId]

}
