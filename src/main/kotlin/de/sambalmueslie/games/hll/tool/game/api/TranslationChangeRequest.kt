package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObjectChangeRequest

data class TranslationChangeRequest(
    val lang: String
) : BusinessObjectChangeRequest {
    override fun valid(): Boolean {
        return lang.isNotBlank()
    }
}
