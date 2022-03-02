package de.sambalmueslie.games.hll.tool.logic.server.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class ServerConnectionChangeRequest(
    val host: String,
    val port: Int,
    val password: String
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return host.isNotBlank() && port > 0 && password.isNotBlank()
    }
}
