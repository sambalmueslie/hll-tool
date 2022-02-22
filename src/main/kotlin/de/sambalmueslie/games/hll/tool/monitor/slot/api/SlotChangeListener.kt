package de.sambalmueslie.games.hll.tool.monitor.slot.api

import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance

interface SlotChangeListener {
    fun handleSlotChanged(instance: ServerInstance, event: SlotsChangeEvent)
}
