package de.sambalmueslie.games.hll.tool.game.api

import de.sambalmueslie.games.hll.tool.common.BusinessObject


data class NationDefinition(
    override val id: Long,
    val key: String
) : BusinessObject
