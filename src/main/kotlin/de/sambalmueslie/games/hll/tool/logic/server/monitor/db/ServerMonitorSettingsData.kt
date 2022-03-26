package de.sambalmueslie.games.hll.tool.logic.server.monitor.db

import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitorSettings
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "ServerMonitorSettings")
@Table(name = "server_monitor_settings")
data class ServerMonitorSettingsData(
    @Id
    val serverId: Long,
    @Column(nullable = false)
    override var mapTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var slotTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var logTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    override var playerTrackingEnabled: Boolean = false
) : ServerMonitorSettings
