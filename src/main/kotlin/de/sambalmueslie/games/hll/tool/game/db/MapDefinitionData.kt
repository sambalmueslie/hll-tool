package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
import de.sambalmueslie.games.hll.tool.game.api.MapDefinition
import de.sambalmueslie.games.hll.tool.game.api.MapDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.MapType
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import javax.persistence.*

@Entity(name = "MapDefinition")
@Table(name = "game_map_definition")
data class MapDefinitionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false, unique = true)
    var key: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: MapType = MapType.WARFARE,
    @Column(nullable = false)
    var attackerId: Long,
    @Column(nullable = false)
    var defenderId: Long,
) : DataObject<MapDefinition, MapDefinitionChangeRequest, EmptyConvertContent> {

    companion object {
        fun create(attacker: NationDefinition, defender: NationDefinition, request: MapDefinitionChangeRequest) =
            MapDefinitionData(0, request.key, request.type, attacker.id, defender.id)
    }

    override fun convert(content: EmptyConvertContent) = MapDefinition(id, key, type, attackerId, defenderId)

    override fun update(request: MapDefinitionChangeRequest) {
        key = request.key
        type = request.type
    }
}
