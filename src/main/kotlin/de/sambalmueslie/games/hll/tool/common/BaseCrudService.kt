package de.sambalmueslie.games.hll.tool.common


import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository
import io.micronaut.security.authentication.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseCrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O, out ConvertContent>>(
    private val repository: PageableRepository<D, Long>,
    private val logger: Logger
) : BaseService<T>(logger), CrudService<T, O, D> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(BaseCrudService::class.java)
    }

    override fun get(objId: Long) = getData(objId)?.let { convert(it) }
    override fun getData(objId: Long) = repository.findByIdOrNull(objId)
    abstract fun convert(data: D): T

    override fun getAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable).map { convert(it) }
    }

    final override fun delete(auth: Authentication, objId: Long) {
        val data = repository.findByIdOrNull(objId) ?: return logger.error("Cannot delete unknown element $objId")
        delete(auth, data)
    }

    final override fun delete(auth: Authentication, obj: D) {
        prepareDeletion(auth, obj)
        repository.delete(obj)
        handleDeletion(auth, obj)
    }

    protected open fun prepareDeletion(auth: Authentication, data: D) {
        // intentionally left empty
    }

    protected open fun handleDeletion(auth: Authentication, data: D) {
        // intentionally left empty
    }


}
