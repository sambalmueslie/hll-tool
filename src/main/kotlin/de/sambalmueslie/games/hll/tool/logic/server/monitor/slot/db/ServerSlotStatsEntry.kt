package de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.db

import java.time.LocalDateTime
import java.time.ZoneOffset
import jakarta.persistence.*

@Entity(name = "ServerSlotStatsEntry")
@Table(name = "server_slot_stats")
data class ServerSlotStatsEntry(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    var active: Int = 0,
    @Column(nullable = false)
    var available: Int = 0,
    @Column(nullable = false)
    var serverId: Long = -1,
    @Column(nullable = false)
    var mapId: Long? = null,
    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
)
