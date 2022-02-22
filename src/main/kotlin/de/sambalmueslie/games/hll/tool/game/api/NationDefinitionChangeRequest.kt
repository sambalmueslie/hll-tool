package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class NationDefinitionChangeRequest(
    val key: String
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return key.isNotBlank()
    }
}
