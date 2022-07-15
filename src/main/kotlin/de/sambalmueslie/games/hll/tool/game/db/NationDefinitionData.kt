package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import de.sambalmueslie.games.hll.tool.game.api.NationDefinitionChangeRequest
import jakarta.persistence.*

@Entity(name = "NationDefinition")
@Table(name = "game_nation_definition")
data class NationDefinitionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false, unique = true)
    var key: String = "",
) : DataObject<NationDefinition, NationDefinitionChangeRequest> {

    companion object {
        fun create(request: NationDefinitionChangeRequest) = NationDefinitionData(0, request.key)
    }

    override fun convert() = NationDefinition(id, key)

    override fun update(request: NationDefinitionChangeRequest) {
        key = request.key
    }
}
