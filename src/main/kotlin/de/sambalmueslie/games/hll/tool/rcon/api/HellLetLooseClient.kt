package de.sambalmueslie.games.hll.tool.rcon.api

import de.sambalmueslie.games.hll.tool.rcon.Slots
import java.util.concurrent.Future

interface HellLetLooseClient {
    fun connect(): Future<Boolean>
    fun getServerName(): String
    fun getMap(): String
    fun getSlots(): Slots
    fun getMapsInRotation(): List<String>
    fun disconnect()
}
