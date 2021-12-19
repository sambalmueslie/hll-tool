package de.sambalmueslie.games.hll.tool.monitor.server.api

import de.sambalmueslie.games.hll.tool.rcon.Slots

interface Server {
    val id: Long
    val name: String
    val mapsInRotation: Set<String>
    fun getMap(): String
    fun getSlots(): Slots
}
