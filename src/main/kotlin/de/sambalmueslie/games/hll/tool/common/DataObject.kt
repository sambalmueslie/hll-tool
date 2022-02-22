package de.sambalmueslie.games.hll.tool.common

interface DataObject<T : BusinessObject, O : BusinessObjectChangeRequest, C : ConvertContent> : BusinessObject {
    fun convert(content: C): T
    fun update(request: O)
}
