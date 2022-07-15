package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.game.api.MapDefinition
import de.sambalmueslie.games.hll.tool.game.api.MapDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.MapType
import jakarta.persistence.*

@Entity(name = "MapDefinition")
@Table(name = "game_map_definition")
data class MapDefinitionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false, unique = true)
    var key: String = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: MapType = MapType.WARFARE,
    @Column(nullable = false)
    var attackerId: Long = 0,
    @Column(nullable = false)
    var defenderId: Long = 0,
) : DataObject<MapDefinition, MapDefinitionChangeRequest> {

    companion object {

        fun create(request: MapDefinitionChangeRequest): MapDefinitionData {
            return MapDefinitionData(0, request.key, request.type, request.attackerId, request.defenderId)
        }
    }

    override fun convert() = MapDefinition(id, key, type, attackerId, defenderId)

    override fun update(request: MapDefinitionChangeRequest) {
        key = request.key
        type = request.type
    }
}
