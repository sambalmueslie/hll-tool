package de.sambalmueslie.games.hll.tool.rcon


import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HllAPI(config: RconClientConfig) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(HllAPI::class.java)
    }


    private val client = RconClient(config)

    fun connect() = client.connect()

    fun getServerName() = client.sendCommand("get name")
    fun getMap() = client.sendCommand("get map")
    fun getSlots() = client.sendCommand("get slots").let { Slots.parse(it) }
    fun getMapsInRotation() = client.sendCommand("rotlist").split("\n")
    fun disconnect() = client.disconnect()

}
