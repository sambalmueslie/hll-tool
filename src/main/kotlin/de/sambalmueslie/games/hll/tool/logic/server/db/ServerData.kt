package de.sambalmueslie.games.hll.tool.logic.server.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerChangeRequest
import javax.persistence.*

@Entity(name = "Server")
@Table(name = "server")
data class ServerData(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,
    @Column()
    override var name: String,
    @Column()
    override var description: String
) : DataObject<Server, ServerChangeRequest>, Server {
    companion object {
        fun create(request: ServerChangeRequest): ServerData {
            return ServerData(0, request.name, request.description)
        }
    }

    override fun convert() = this

    override fun update(request: ServerChangeRequest) {
        name = request.name
        description = request.description
    }


}
