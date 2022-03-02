package de.sambalmueslie.games.hll.tool.logic.server.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

interface Server : BusinessObject {
    val name: String
    val description: String
}
