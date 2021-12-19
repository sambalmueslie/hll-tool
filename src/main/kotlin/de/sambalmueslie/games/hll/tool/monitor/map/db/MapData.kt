package de.sambalmueslie.games.hll.tool.monitor.map.db

import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity(name = "Map")
@Table(name = "map")
data class MapData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    var name: String = "",
    @Column(nullable = false)
    var serverId: Long = -1,
    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
)
