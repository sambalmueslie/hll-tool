package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class TranslationEntryChangeRequest(
    val translationId: Long,
    val key: String,
    val value: String
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return key.isNotBlank() && value.isNotBlank()
    }
}
