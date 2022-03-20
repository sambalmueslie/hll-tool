package de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api

import java.time.LocalDateTime

interface ServerMap {
    val id: Long
    val name: String
    val serverId: Long
    val timestamp: LocalDateTime
}
