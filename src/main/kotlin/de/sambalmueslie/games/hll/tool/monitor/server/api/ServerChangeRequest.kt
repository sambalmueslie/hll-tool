package de.sambalmueslie.games.hll.tool.monitor.server.api

data class ServerChangeRequest(
    val name: String,
    val host: String,
    val port: Int,
    val password: String,
    val settings: ServerSettingsChangeRequest
)
