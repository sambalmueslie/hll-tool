package de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.api

import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.rcon.Slots

interface ServerSlotsChangeListener {
    fun handleSlotsChanged(client: ServerClient, slots: Slots)
}
