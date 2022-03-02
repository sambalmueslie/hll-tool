package de.sambalmueslie.games.hll.tool.logic.server.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnection
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnectionChangeRequest
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class ServerConnectionData(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,
    @Column()
    var serverId: Long,
    @Column()
    override var host: String,
    @Column()
    override var port: Int,
    @Column()
    override var password: String,
) : DataObject<ServerConnection, ServerConnectionChangeRequest>, ServerConnection {
    companion object {
        fun create(request: ServerConnectionChangeRequest, server: Server): ServerConnectionData {
            return ServerConnectionData(0, server.id, request.host, request.port, request.password)
        }
    }

    override fun convert(): ServerConnection = this

    override fun update(request: ServerConnectionChangeRequest) {
        host = request.host
        port = request.port
        password = request.password
    }

}
