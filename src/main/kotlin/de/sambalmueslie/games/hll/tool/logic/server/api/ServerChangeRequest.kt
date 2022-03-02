package de.sambalmueslie.games.hll.tool.logic.server.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class ServerChangeRequest(
    val name: String,
    val description: String,
    val connection: ServerConnectionChangeRequest
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return name.isNotBlank()
    }
}
