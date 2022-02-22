package de.sambalmueslie.games.hll.tool.common

abstract class CrudController<T : BusinessObject, O : BusinessObjectChangeRequest>(
) : CrudAPI<T, O> {

}
