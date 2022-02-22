package de.sambalmueslie.games.hll.tool.monitor.slot


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.monitor.map.MapChangeEventProcessor
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstanceProcessor
import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotChangeListener
import de.sambalmueslie.games.hll.tool.monitor.slot.api.SlotsChangeEvent
import de.sambalmueslie.games.hll.tool.monitor.slot.db.SlotChangeRepository
import de.sambalmueslie.games.hll.tool.monitor.slot.db.SlotData
import de.sambalmueslie.games.hll.tool.rcon.Slots
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
class SlotChangeEventProcessor(
    private val repository: SlotChangeRepository,
    private val mapChangeEventProcessor: MapChangeEventProcessor,
    private val listener: Set<SlotChangeListener>
) : ServerInstanceProcessor {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SlotChangeEventProcessor::class.java)
    }

    private var currentSlots = mutableMapOf<Long, Slots>()

    private val currentSlotCache: LoadingCache<Long, SlotData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .build { serverId -> repository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    override fun runCycle(instance: ServerInstance) {
        if (instance.isSlotTrackingEnabled()) updateServerSlot(instance)
    }

    private fun updateServerSlot(instance: ServerInstance) {
        val slots = instance.getSlots()
        val current = currentSlotCache[instance.id]
        val map = mapChangeEventProcessor.getCurrentMap(instance.id) ?: return
        if (!hasChanged(current, slots, map)) return
        handleSlotsChange(instance, slots, map)
    }

    private fun hasChanged(current: SlotData?, slots: Slots, map: MapData): Boolean {
        if (current == null) return true
        if (current.mapId != map.id) return true
        if (current.active != slots.active) return true
        return false
    }

    private fun handleSlotsChange(instance: ServerInstance, slots: Slots, map: MapData) {
        if (!slots.isValid() || slots.active <= 0) return
        logger.info("Handle slot change from ${currentSlots[instance.id]} -> $slots")
        currentSlots[instance.id] = slots
        val timestamp = LocalDateTime.now(ZoneOffset.UTC)
        repository.save(SlotData(0, slots.active, slots.available, map.id, instance.id, timestamp))

        val event = SlotsChangeEvent(slots, timestamp)
        listener.forEachWithTryCatch { it.handleSlotChanged(instance, event) }
    }

}
