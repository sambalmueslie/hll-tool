package de.sambalmueslie.games.hll.tool.logic.server.monitor.db

import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitorSettings
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ServerMonitorSettings")
@Table(name = "server_monitor_settings")
data class ServerMonitorSettingsData(
    @Id
    val serverId: Long = 0,
    @Column(nullable = false)
    override var mapTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var slotTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var logTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var playerTrackingEnabled: Boolean = false
) : ServerMonitorSettings
