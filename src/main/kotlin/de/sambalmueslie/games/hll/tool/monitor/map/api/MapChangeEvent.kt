package de.sambalmueslie.games.hll.tool.monitor.map.api

import java.time.LocalDateTime

data class MapChangeEvent(
    val id: Long,
    val map: String,
    val timestamp: LocalDateTime
)
