package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

data class MapDefinition(
    override val id: Long,
    val key: String,
    val type: MapType,
    val attackerId: Long,
    val defenderId: Long
) : BusinessObject
