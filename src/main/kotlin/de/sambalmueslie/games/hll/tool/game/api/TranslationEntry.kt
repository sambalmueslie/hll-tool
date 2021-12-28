package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject

data class TranslationEntry(
    override val id: Long,
    val key: String,
    val value: String
) : BusinessObject
