package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

data class Translation(
    override val id: Long,
    val lang: String
) : BusinessObject
