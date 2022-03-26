package de.sambalmueslie.games.hll.tool.logic.server.info.db

import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.info.api.ServerInfo
import de.sambalmueslie.games.hll.tool.rcon.Slots
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "ServerInfo")
@Table(name = "server_info")
data class ServerInfoData(
    @Id
    val serverId: Long,
    @Column()
    val name: String,
    @Column()
    var map: String = "",
    @Column()
    var slotsActive: Int = -1,
    @Column()
    var slotsAvailable: Int = -1
) {
    fun convert(server: Server) = ServerInfo(server, name, map, Slots(slotsActive, slotsAvailable))
}
