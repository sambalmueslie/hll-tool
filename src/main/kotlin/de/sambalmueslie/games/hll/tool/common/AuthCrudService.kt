package de.sambalmueslie.games.hll.tool.common

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication

interface AuthCrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O>> {
    fun getAll(auth: Authentication, pageable: Pageable): Page<T>
    fun get(auth: Authentication, objId: Long): T?
    fun create(auth: Authentication, request: O): T?
    fun update(auth: Authentication, objId: Long, request: O): T?
    fun delete(auth: Authentication, objId: Long)
}
