package de.sambalmueslie.games.hll.tool.monitor.server.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "ServerSettings")
@Table(name = "server_settings")
data class ServerSettingsData(
    @Id
    var id: Long = 0,
    @Column(nullable = false)
    var mapTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    var slotTrackingEnabled: Boolean = false,
    @Column(nullable = false)
    var logTrackingEnabled: Boolean = false
)
