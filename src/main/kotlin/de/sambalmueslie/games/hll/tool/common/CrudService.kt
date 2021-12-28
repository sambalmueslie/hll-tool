package de.sambalmueslie.games.hll.tool.common

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication

interface CrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O, out ConvertContent>> {
    fun getAll(pageable: Pageable): Page<T>

    fun get(objId: Long): T?
    fun getData(objId: Long): D?

    fun create(auth: Authentication, request: O): T?

    fun update(auth: Authentication, objId: Long, request: O): T?
    fun update(auth: Authentication, obj: D, request: O): T?

    fun delete(auth: Authentication, objId: Long)
    fun delete(auth: Authentication, obj: D)
}
