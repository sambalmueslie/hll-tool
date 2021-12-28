package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class MapDefinitionChangeRequest(
    val key: String,
    val type: MapType,
    val attackerId: Long,
    val defenderId: Long
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return key.isNotBlank() && attackerId > 0 && defenderId > 0
    }
}
