package de.sambalmueslie.games.hll.tool.features.mapvote.db

import jakarta.persistence.*

@Entity(name = "MapVoteResult")
@Table(name = "map_vote_result")
data class MapVoteResultData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    var serverId: Long = 0,
    @Column(nullable = false)
    var currentMapId: Long= 0,
)
