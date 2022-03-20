package de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db

import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api.ServerMap
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity(name = "ServerMap")
@Table(name = "server_map")
data class ServerMapData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false)
    override var name: String = "",
    @Column(nullable = false)
    override var serverId: Long = -1,
    @Column(nullable = false)
    override var timestamp: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
) : ServerMap

