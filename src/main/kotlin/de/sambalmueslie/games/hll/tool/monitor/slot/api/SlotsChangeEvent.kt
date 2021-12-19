package de.sambalmueslie.games.hll.tool.monitor.slot.api


import de.sambalmueslie.games.hll.tool.rcon.Slots
import java.time.LocalDateTime

data class SlotsChangeEvent(
    val slots: Slots,
    val timestamp: LocalDateTime
)
