package de.sambalmueslie.games.hll.tool.monitor.slot


import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotsChangeEvent
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SlotStatsProcessor {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SlotStatsProcessor::class.java)
    }

    fun slotChangeEvent(serverName: String, event: SlotsChangeEvent) {
        logger.info("[$serverName] - slot event $event")
        //TODO("Not yet implemented")
    }
}
