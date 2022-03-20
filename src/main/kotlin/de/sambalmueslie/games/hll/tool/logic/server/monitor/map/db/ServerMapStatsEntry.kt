package de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db

import de.sambalmueslie.games.hll.tool.util.DurationConverter
import io.micronaut.data.annotation.TypeDef
import io.micronaut.data.model.DataType
import java.time.Duration
import javax.persistence.*

@Entity(name = "ServerMapStatsEntry")
@Table(name = "server_map_stats")
data class ServerMapStatsEntry(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    var mapId: Long = -1,
    @Column(nullable = false)
    var serverId: Long = -1,
    @Column
    @field:TypeDef(type = DataType.LONG, converter = DurationConverter::class)
    var duration: Duration = Duration.ZERO
)
