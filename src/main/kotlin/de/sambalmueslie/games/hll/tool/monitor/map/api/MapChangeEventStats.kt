package de.sambalmueslie.games.hll.tool.monitor.map.api

import java.time.Duration
import java.time.LocalDateTime

data class MapChangeEventStats(
    val id: Long,
    val map: String,
    val timestamp: LocalDateTime,
    val duration: Duration
)
