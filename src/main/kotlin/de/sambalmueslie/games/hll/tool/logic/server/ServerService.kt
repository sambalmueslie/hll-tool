package de.sambalmueslie.games.hll.tool.logic.server


import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.common.CrudService
import de.sambalmueslie.games.hll.tool.logic.server.api.*
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
) : BaseAuthCrudService<Server, ServerChangeRequest, ServerData>(listeners, repository, logger), CrudService<Server, ServerChangeRequest, ServerData> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerService::class.java)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<Server> {
        // TODO check auth
        return getAll(pageable)
    }

    override fun getAll(pageable: Pageable): Page<Server> {
        return repository.findAll(pageable).map { it.convert() }
    }


    override fun get(auth: Authentication, objId: Long): Server? {
        // TODO check auth
        return get(objId)
    }

    override fun get(objId: Long): Server? {
        return repository.findByIdOrNull(objId)
    }

    override fun convert(request: ServerChangeRequest): ServerData {
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

    private fun update(request: ServerChangeRequest, result: Server) {
        val existing = connectionRepository.findByIdOrNull(result.id)
        if (existing == null) {
            val data = connectionRepository.save(ServerConnectionData.create(request.connection, result)).convert()
            connectionListeners.forEachWithTryCatch { it.created(data) }
        } else {
            existing.update(request.connection)
            val connection = connectionRepository.update(existing).convert()
            connectionListeners.forEachWithTryCatch { it.updated(connection) }
        }
    }

    override fun handleDeleted(result: Server) {
        val data = connectionRepository.findByIdOrNull(result.id) ?: return
        connectionRepository.delete(data)
        val connection = data.convert()
        connectionListeners.forEachWithTryCatch { it.deleted(connection) }
    }

    fun getConnection(obj: Server): ServerConnection? {
        return connectionRepository.findByIdOrNull(obj.id)?.convert()
    }

}
