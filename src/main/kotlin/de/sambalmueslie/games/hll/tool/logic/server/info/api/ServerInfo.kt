package de.sambalmueslie.games.hll.tool.logic.server.info.api

import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.rcon.Slots

data class ServerInfo(
    val server: Server,
    val name: String,
    val map: String,
    val slots: Slots
)
