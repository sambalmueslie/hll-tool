package de.sambalmueslie.games.hll.tool.common


import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import io.micronaut.data.repository.PageableRepository
import io.micronaut.security.authentication.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseAuthCrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O>>(
    private val listeners: Set<BusinessObjectChangeListener<T>>,
    private val repository: PageableRepository<D, Long>,
    private val logger: Logger
) : AuthCrudService<T, O, D> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(BaseAuthCrudService::class.java)
    }

    final override fun create(auth: Authentication, request: O): T? {
        // TODO check auth
        if (!validate(request)) {
            logger.error("Failed to create: Invalid request $request")
            return null
        }

        logger.info("Create $request")
        val data = create(request) ?: return null
        val result = repository.save(data).convert()
        handleCreated(request, result)
        listeners.forEachWithTryCatch { it.created(result) }
        return result
    }

    protected open fun handleCreated(request: O, result: T) {}
    protected open fun handleUpdated(request: O, result: T) {}
    protected open fun handleDeleted(result: T) {}

    protected abstract fun create(request: O): D?

    final override fun update(auth: Authentication, objId: Long, request: O): T? {
        // TODO check auth
        val current = repository.findByIdOrNull(objId) ?: return create(auth, request)
        if (!validate(request)) {
            logger.error("Failed to update: Invalid request $request")
            return null
        }

        logger.info("Update $request")
        current.update(request)
        val data = repository.update(current).convert()
        handleUpdated(request, data)
        listeners.forEachWithTryCatch { it.updated(data) }
        return data
    }

    protected abstract fun validate(request: O): Boolean

    final override fun delete(auth: Authentication, objId: Long) {
        // TODO check auth
        val data = repository.findByIdOrNull(objId) ?: return
        logger.info("Delete $objId $data")
        repository.delete(data)
        val result = data.convert()
        handleDeleted(result)
        listeners.forEachWithTryCatch { it.deleted(result) }
    }
}
