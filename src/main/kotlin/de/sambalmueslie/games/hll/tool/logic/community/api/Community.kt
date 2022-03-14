package de.sambalmueslie.games.hll.tool.logic.community.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

interface Community : BusinessObject {
    val name: String
    val description: String
}
