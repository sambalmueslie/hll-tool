package de.sambalmueslie.games.hll.tool.logic.server


import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerChangeRequest
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerConnectionChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerConnectionData
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerConnectionRepository
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerData
import de.sambalmueslie.games.hll.tool.logic.server.db.ServerRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ServerService(
    private val repository: ServerRepository,
    listeners: Set<ServerChangeListener>,
    private val connectionRepository: ServerConnectionRepository,
    private val connectionListeners: Set<ServerConnectionChangeListener>
) : BaseAuthCrudService<Server, ServerChangeRequest, ServerData>(listeners, repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerService::class.java)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<Server> {
        // TODO check auth
        return repository.findAll(pageable).map { it.convert() }
    }

    override fun get(auth: Authentication, objId: Long): Server? {
        // TODO check auth
        return repository.findByIdOrNull(objId)
    }

    override fun create(request: ServerChangeRequest): ServerData {
        return ServerData.create(request)
    }

    override fun validate(request: ServerChangeRequest): Boolean {
        if (!request.valid()) return false
        val existing = repository.findByName(request.name)
        if (existing.isNotEmpty()) return false

        return true
    }

    override fun handleCreated(request: ServerChangeRequest, result: Server) {
        update(request, result)
    }

    override fun handleUpdated(request: ServerChangeRequest, result: Server) {
        update(request, result)
    }

    private fun update(request: ServerChangeRequest, result: Server){
        val existing = connectionRepository.findByServerId(result.id)
        if (existing.isEmpty()) {
            val data = connectionRepository.save(ServerConnectionData.create(request.connection, result)).convert()
            connectionListeners.forEachWithTryCatch { it.created(data) }
        } else {
            val data = existing.first()
            data.update(request.connection)
            val connection = connectionRepository.update(data).convert()
            connectionListeners.forEachWithTryCatch { it.updated(connection) }

            if (existing.size > 1) {
                connectionRepository.deleteAll(existing.subList(1, existing.size))
            }

        }
    }

    override fun handleDeleted(result: Server) {
        val data = connectionRepository.findByServerId(result.id)
        connectionRepository.deleteAll(data)
        val connections = data.map { it.convert() }
        connections.forEach { c ->
            connectionListeners.forEachWithTryCatch { it.deleted(c) }
        }

    }


}
