package de.sambalmueslie.games.hll.tool.common

interface DataObject<T : BusinessObject, O : BusinessObjectChangeRequest> : BusinessObject {
    fun convert(): T
    fun update(request: O)
}
