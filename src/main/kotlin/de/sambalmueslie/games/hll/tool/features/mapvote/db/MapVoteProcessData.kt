package de.sambalmueslie.games.hll.tool.features.mapvote.db

import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import javax.persistence.*

@Entity(name = "MapVoteProcess")
@Table(name = "map_vote_process")
data class MapVoteProcessData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    val serverId: Long,
    @Column(nullable = false)
    val mapId: Long,
    @Column(nullable = false)
    val discordMessageId: Long,
) {


    @Column(nullable = true)
    private var _votes: String? = null

    @Transient
    fun setVotes(mapsForVote: List<MapInfo>) {

        TODO("Not yet implemented")
    }
}
