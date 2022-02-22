package de.sambalmueslie.games.hll.tool.game.api

data class MapInfo(
    val id: Long,
    val key: String,
    val text: String,
    val type: MapType,
    val attacker: NationInfo,
    val defender: NationInfo
)
