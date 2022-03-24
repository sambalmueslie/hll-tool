package de.sambalmueslie.games.hll.tool.rcon


import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HellLetLooseServerClient(config: RconClientConfig) : HellLetLooseClient {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HellLetLooseServerClient::class.java)

        val maps = setOf(
            "foy_warfare",
            "stmariedumont_off_us",
            "kursk_offensive_rus",
            "utahbeach_offensive_us",
            "stmereeglise_warfare",
            "carentan_offensive_us",
            "hill400_warfare",
            "stmariedumont_off_ger",
            "hurtgenforest_offensive_ger",
            "stalingrad_warfare",
            "foy_offensive_ger",
            "utahbeach_offensive_ger",
            "carentan_warfare",
            "kursk_warfare",
            "purpleheartlane_offensive_us",
            "stmereeglise_offensive_us",
            "utahbeach_warfare",
            "stalingrad_offensive_ger",
            "hurtgenforest_warfare_V2",
            "stmereeglise_offensive_ger",
            "hill400_offensive_US",
            "omahabeach_offensive_us",
            "purpleheartlane_warfare",
            "kursk_offensive_ger",
            "stalingrad_offensive_rus",
            "stmariedumont_warfare",
            "hurtgenforest_offensive_US",
            "purpleheartlane_offensive_ger",
            "carentan_offensive_ger",
            "foy_offensive_us",
            "hill400_offensive_ger"
        )

    }


    private val client = RconClient(config)

    override fun connect() = client.connect()
    override fun getServerName() = client.sendCommand("get name")
    override fun getMap() = client.sendCommand("get map")
    override fun getSlots() = client.sendCommand("get slots").let { Slots.parse(it) }
    override fun getMapsInRotation() = client.sendCommand("rotlist").split("\n")
    override fun disconnect() = client.disconnect()

}
