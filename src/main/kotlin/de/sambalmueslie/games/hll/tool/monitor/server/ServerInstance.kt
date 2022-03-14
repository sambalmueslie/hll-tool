package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnection
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsData
import de.sambalmueslie.games.hll.tool.rcon.HllAPI
import de.sambalmueslie.games.hll.tool.rcon.RconClientConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServerInstance(
    private val data: Server,
    private val connection: ServerConnection,
    private val settings: ServerSettingsData
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerInstance::class.java)
    }

    private val hllAPI = HllAPI(RconClientConfig(connection.host, connection.port, connection.password))
    var name: String = "Unknown"
        private set

    private val _mapsInRotation = mutableSetOf<String>()
    val mapsInRotation: Set<String> = _mapsInRotation

    init {
        try {
            if (hllAPI.connect().get()) {
                name = hllAPI.getServerName()
                _mapsInRotation.addAll(hllAPI.getMapsInRotation().filter { it.isNotBlank() })
                logger.info("CONNECTION ESTABLISHED TO '$name'")
            }
        } catch (e: Exception) {
            logger.error("Failed to connect", e)
        }
    }

    val id: Long = data.id
    fun getMap() = hllAPI.getMap()
    fun getSlots() = hllAPI.getSlots()

    fun isEnabled(): Boolean {
        return settings.mapTrackingEnabled || settings.slotTrackingEnabled
    }

    fun isMapTrackingEnabled() = settings.mapTrackingEnabled
    fun isSlotTrackingEnabled() = settings.slotTrackingEnabled
    fun isLogTrackingEnabled() = settings.logTrackingEnabled

    fun disconnect() {
        hllAPI.disconnect()
    }
}
