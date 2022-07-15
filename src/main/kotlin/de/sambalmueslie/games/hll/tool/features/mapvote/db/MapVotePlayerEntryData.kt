package de.sambalmueslie.games.hll.tool.features.mapvote.db

import jakarta.persistence.*

@Entity(name = "MapVotePlayerEntry")
@Table(name = "map_vote_player_entry")
data class MapVotePlayerEntryData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    val voteProcessId: Long = 0,
)
