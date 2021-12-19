package de.sambalmueslie.games.hll.tool.monitor.slot


import de.sambalmueslie.games.hll.tool.monitor.map.MapChangeEventProcessor
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.monitor.server.ServerProcessor
import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotsChangeEvent
import de.sambalmueslie.games.hll.tool.monitor.slot.db.SlotChangeRepository
import de.sambalmueslie.games.hll.tool.monitor.slot.db.SlotData
import de.sambalmueslie.games.hll.tool.monitor.slot.kafka.SlotChangeEventProducer
import de.sambalmueslie.games.hll.tool.rcon.Slots
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
open class SlotChangeEventProcessor(
    private val serverProcessor: ServerProcessor,
    private val eventProducer: SlotChangeEventProducer,
    private val repository: SlotChangeRepository,
    private val mapChangeEventProcessor: MapChangeEventProcessor,
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SlotChangeEventProcessor::class.java)
    }

    private var currentSlots = mutableMapOf<Long, Slots>()

    @Scheduled(fixedDelay = "2s")
    open fun updateSlot() {
        serverProcessor.instances.filter { it.isSlotTrackingEnabled() }.forEach { updateServerSlot(it) }
    }

    private fun updateServerSlot(instance: ServerInstance) {
        val slots = instance.getSlots()
        if (currentSlots[instance.id] == slots) return
        handleSlotsChange(instance, slots)
    }

    private fun handleSlotsChange(instance: ServerInstance, slots: Slots) {
        if (!slots.isValid() || slots.active <= 0) return
        logger.info("Handle slot change from ${currentSlots[instance.id]} -> $slots")
        currentSlots[instance.id] = slots
        val timestamp = LocalDateTime.now(ZoneOffset.UTC)
        eventProducer.sendEvent(instance.name, SlotsChangeEvent(slots, timestamp))
        val map = mapChangeEventProcessor.getCurrentMap(instance.id) ?: return
        repository.save(SlotData(0, slots.active, slots.available, map.id, instance.id, timestamp))
    }

}
