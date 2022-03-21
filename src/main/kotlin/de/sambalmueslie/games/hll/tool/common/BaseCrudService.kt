package de.sambalmueslie.games.hll.tool.common


import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository
import org.slf4j.Logger

abstract class BaseCrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O>>(
    private val l: Set<BusinessObjectChangeListener<T>>,
    private val repository: PageableRepository<D, Long>,
    private val logger: Logger
) : CrudService<T, O, D> {

    private val listeners = l.toMutableSet()

    override fun unregister(listener: BusinessObjectChangeListener<T>) {
        listeners.add(listener)
    }

    override fun register(listener: BusinessObjectChangeListener<T>) {
        listeners.remove(listener)
    }

    override fun getAll(): List<T> {
        return repository.findAll().map { it.convert() }
    }

    override fun get(objId: Long): T? {
        return repository.findByIdOrNull(objId)?.convert()
    }

    override fun getAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable).map { it.convert() }
    }

    final override fun create(request: O): T? {
        if (!validate(request)) {
            logger.error("Failed to create: Invalid request $request")
            return null
        }

        logger.info("Create $request")
        val data = convert(request) ?: return null
        val result = repository.save(data).convert()
        handleCreated(request, result)
        listeners.forEachWithTryCatch { it.created(result) }
        return result
    }

    protected open fun handleCreated(request: O, result: T) {
        // intentionally left empty
    }

    protected open fun handleUpdated(request: O, result: T) {
        // intentionally left empty
    }

    protected open fun handleDeleted(result: T) {
        // intentionally left empty
    }

    protected abstract fun convert(request: O): D?

    final override fun update(objId: Long, request: O): T? {
        val current = repository.findByIdOrNull(objId) ?: return create(request)
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

    final override fun delete(objId: Long) {
        val data = repository.findByIdOrNull(objId) ?: return
        logger.info("Delete $objId $data")
        repository.delete(data)
        val result = data.convert()
        handleDeleted(result)
        listeners.forEachWithTryCatch { it.deleted(result) }
    }

}
