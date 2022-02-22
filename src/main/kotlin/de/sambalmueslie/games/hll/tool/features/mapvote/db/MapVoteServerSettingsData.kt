package de.sambalmueslie.games.hll.tool.features.mapvote.db

import javax.persistence.*

@Entity(name = "MapVoteServerSettings")
@Table(name = "map_vote_server_settings")
data class MapVoteServerSettingsData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    @Column(nullable = true)
    var serverId: Long? = null,
    @Column(nullable = false)
    var enabled: Boolean = false,
    @Column(nullable = false)
    var adminChannelId: Long = -1,
    @Column(nullable = false)
    var userChannelId: Long = -1,
    @Column(nullable = false)
    var guildId: Long,
    @Column(nullable = false)
    var mapVoteAmount: Int = 8
) {

}
