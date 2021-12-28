package de.sambalmueslie.games.hll.tool.monitor.server.api


data class ServerSettingsChangeRequest(
    val mapTrackingEnabled: Boolean,
    val slotTrackingEnabled: Boolean,
    val logTrackingEnabled: Boolean
)
