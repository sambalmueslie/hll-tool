package de.sambalmueslie.games.hll.tool.common

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface CrudService<T : BusinessObject, O : BusinessObjectChangeRequest, D : DataObject<T, O>> {
    fun getAll(pageable: Pageable): Page<T>
    fun getAll(): List<T>
    fun get(objId: Long): T?
    fun create(request: O): T?
    fun update(objId: Long, request: O): T?
    fun delete(objId: Long)

    fun register(listener: BusinessObjectChangeListener<T>)
    fun unregister(listener: BusinessObjectChangeListener<T>)
}
