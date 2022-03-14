package de.sambalmueslie.games.hll.tool.logic.community.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.logic.community.api.Community
import de.sambalmueslie.games.hll.tool.logic.community.api.CommunityChangeRequest
import javax.persistence.*

@Entity(name = "Community")
@Table(name = "community")
data class CommunityData(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,
    @Column()
    override var name: String,
    @Column()
    override var description: String
) : DataObject<Community, CommunityChangeRequest>, Community {

    companion object {
        fun create(request: CommunityChangeRequest): CommunityData {
            return CommunityData(0, request.name, request.description)
        }
    }

    override fun convert() = this

    override fun update(request: CommunityChangeRequest) {
        this.name = request.name
        this.description = request.description
    }
}
