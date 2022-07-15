package de.sambalmueslie.games.hll.tool.features.mapvote.db

import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import jakarta.persistence.*

@Entity(name = "MapVoteProcess")
@Table(name = "map_vote_process")
data class MapVoteProcessData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    val serverId: Long = 0,
    @Column(nullable = false)
    val mapId: Long = 0,
    @Column(nullable = false)
    val discordMessageId: Long = 0,
) {


    @Column(nullable = true)
    private var _votes: String? = null

    @Transient
    fun setVotes(mapsForVote: List<MapInfo>) {

        TODO("Not yet implemented")
    }
}
