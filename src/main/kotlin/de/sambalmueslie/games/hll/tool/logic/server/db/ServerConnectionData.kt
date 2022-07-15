package de.sambalmueslie.games.hll.tool.logic.server.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnection
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnectionChangeRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ServerConnection")
@Table(name = "server_connection")
data class ServerConnectionData(
    @Id
    override var id: Long = 0,
    @Column()
    override var host: String = "",
    @Column()
    override var port: Int = 0,
    @Column()
    override var password: String = "",
) : DataObject<ServerConnection, ServerConnectionChangeRequest>, ServerConnection {
    companion object {
        fun create(request: ServerConnectionChangeRequest, server: Server): ServerConnectionData {
            return ServerConnectionData(server.id, request.host, request.port, request.password)
        }
    }

    override fun convert(): ServerConnection = this

    override fun update(request: ServerConnectionChangeRequest) {
        host = request.host
        port = request.port
        password = request.password
    }

}
