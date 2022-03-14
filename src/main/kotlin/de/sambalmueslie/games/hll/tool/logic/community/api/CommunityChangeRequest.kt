package de.sambalmueslie.games.hll.tool.logic.community.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class CommunityChangeRequest(
    val name: String,
    val description: String,
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return name.isNotBlank()
    }
}
