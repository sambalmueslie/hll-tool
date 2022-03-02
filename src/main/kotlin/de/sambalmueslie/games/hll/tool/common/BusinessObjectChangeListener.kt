package de.sambalmueslie.games.hll.tool.common

interface BusinessObjectChangeListener<T : BusinessObject> {
    fun created(obj: T)
    fun updated(obj: T)
    fun deleted(obj: T)
}
