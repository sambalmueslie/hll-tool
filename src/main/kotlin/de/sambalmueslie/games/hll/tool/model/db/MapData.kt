package de.sambalmueslie.games.hll.tool.model.db

import de.sambalmueslie.games.hll.tool.model.api.Map
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity(name = "Map")
@Table(name = "map")
data class MapData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false)
    override var name: String = "",
    @Column(nullable = false)
    override var serverId: Long = -1,
    @Column(nullable = false)
    override var timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
) : Map
