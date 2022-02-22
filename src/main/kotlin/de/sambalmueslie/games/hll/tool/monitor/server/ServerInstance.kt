package de.sambalmueslie.games.hll.tool.monitor.server


import de.sambalmueslie.games.hll.tool.monitor.server.api.Server
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerData
import de.sambalmueslie.games.hll.tool.monitor.server.db.ServerSettingsData
import de.sambalmueslie.games.hll.tool.rcon.HllAPI
import de.sambalmueslie.games.hll.tool.rcon.RconClientConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServerInstance(
    private val data: ServerData,
    private val settings: ServerSettingsData
) : Server {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerInstance::class.java)
    }

    private val hllAPI = HllAPI(RconClientConfig(data.host, data.port, data.password))
    override var name: String = "Unknown"
        private set

    private val _mapsInRotation = mutableSetOf<String>()
    override val mapsInRotation: Set<String> = _mapsInRotation

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

    override val id: Long = data.id
    override fun getMap() = hllAPI.getMap()
    override fun getSlots() = hllAPI.getSlots()

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
