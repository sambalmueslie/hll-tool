package de.sambalmueslie.games.hll.tool.model.api

import java.time.LocalDateTime

interface Map {
    val id: Long
    val name: String
    val serverId: Long
    val timestamp: LocalDateTime
}
