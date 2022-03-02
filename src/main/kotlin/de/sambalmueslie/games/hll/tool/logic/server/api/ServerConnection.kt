package de.sambalmueslie.games.hll.tool.logic.server.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

interface ServerConnection : BusinessObject {
    val host: String
    val port: Int
    val password: String
}
