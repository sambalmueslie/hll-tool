package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerRepository
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceReadyEvent
import jakarta.inject.Singleton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ServerProcessor(
    private val repository: ServerRepository,
    private val settingsRepository: ServerSettingsRepository
) : ApplicationEventListener<ServiceReadyEvent> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerProcessor::class.java)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onApplicationEvent(event: ServiceReadyEvent) {
        GlobalScope.async {
            repository.findAll().forEach { setupServer(it) }
        }
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
    fun getInstanceByServerName(name: String) = _instances.values.find { it.name == name }
    fun getInstanceByServerId(serverId: Long) = _instances[serverId]

}
