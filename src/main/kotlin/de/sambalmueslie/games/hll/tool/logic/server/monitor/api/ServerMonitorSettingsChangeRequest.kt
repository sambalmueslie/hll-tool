package de.sambalmueslie.games.hll.tool.logic.server.monitor.api


data class ServerMonitorSettingsChangeRequest(
    val mapTrackingEnabled: Boolean,
    val slotTrackingEnabled: Boolean,
    val logTrackingEnabled: Boolean,
    val playerTrackingEnabled: Boolean
)
