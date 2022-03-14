package de.sambalmueslie.games.hll.tool.common


import io.micronaut.data.repository.PageableRepository
import io.micronaut.security.authentication.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseAuthCrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O>>(
    listeners: Set<BusinessObjectChangeListener<T>>,
    repository: PageableRepository<D, Long>,
    logger: Logger
) : AuthCrudService<T, O, D>, BaseCrudService<T, O, D>(listeners, repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(BaseAuthCrudService::class.java)
    }

    final override fun create(auth: Authentication, request: O): T? {
        // TODO check auth
        return create(request)
    }


    final override fun update(auth: Authentication, objId: Long, request: O): T? {
        // TODO check auth
        return update(objId, request)
    }

    final override fun delete(auth: Authentication, objId: Long) {
        // TODO check auth
        delete(objId)
    }
}
