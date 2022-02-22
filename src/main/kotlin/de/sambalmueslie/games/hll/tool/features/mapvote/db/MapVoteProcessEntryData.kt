package de.sambalmueslie.games.hll.tool.features.mapvote.db

import javax.persistence.*

@Entity(name = "MapVoteProcessEntry")
@Table(name = "map_vote_process_entry")
data class MapVoteProcessEntryData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = false)
    val voteProcessId: Long = 0,
    @Column(nullable = false)
    val mapDefinitionId: Long = 0,
    @Column(nullable = false)
    val votes: Int = 0
)
