package de.sambalmueslie.games.hll.tool.logic.server.monitor


import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnection
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitorSettings
import de.sambalmueslie.games.hll.tool.rcon.RconClientConfig
import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClientFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServerClient(
    server: Server,
    connection: ServerConnection,
    private val settings: ServerMonitorSettings,
    clientFactory: HellLetLooseClientFactory
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ServerClient::class.java)
    }

    private val hllClient = clientFactory.create(RconClientConfig(connection.host, connection.port, connection.password))

    var name: String = "Unknown"
        private set

    private val _mapsInRotation = mutableSetOf<String>()
    val mapsInRotation: Set<String> = _mapsInRotation


    init {
        try {
            if (hllClient.connect().get()) {
                name = hllClient.getServerName()
                _mapsInRotation.addAll(hllClient.getMapsInRotation().filter { it.isNotBlank() })
                logger.info("CONNECTION ESTABLISHED TO '$name'")
            }
        } catch (e: Exception) {
            logger.error("Failed to connect", e)
        }
    }

    val id: Long = server.id
    fun getMap() = hllClient.getMap()
    fun getSlots() = hllClient.getSlots()

    fun isEnabled(): Boolean {
        return settings.mapTrackingEnabled || settings.slotTrackingEnabled
    }

    fun isMapTrackingEnabled() = settings.mapTrackingEnabled
    fun isSlotTrackingEnabled() = settings.slotTrackingEnabled
    fun isLogTrackingEnabled() = settings.logTrackingEnabled
    fun isPlayerTrackingEnabled() = settings.playerTrackingEnabled

    fun disconnect() {
        hllClient.disconnect()
    }
}
