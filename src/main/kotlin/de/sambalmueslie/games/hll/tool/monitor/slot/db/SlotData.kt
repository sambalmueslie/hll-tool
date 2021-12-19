package de.sambalmueslie.games.hll.tool.monitor.slot.db

import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity(name = "Slot")
@Table(name = "slot")
data class SlotData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    var active: Int = 0,
    @Column(nullable = false)
    var available: Int = 0,
    @Column(nullable = false)
    var mapId: Long = -1,
    @Column(nullable = false)
    var serverId: Long = -1,
    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
)
