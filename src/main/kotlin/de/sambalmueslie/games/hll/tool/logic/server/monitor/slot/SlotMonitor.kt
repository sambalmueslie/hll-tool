package de.sambalmueslie.games.hll.tool.logic.server.monitor.slot


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitoringProcessor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.MapMonitor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapData
import de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.db.ServerSlotStatsEntry
import de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.db.ServerSlotStatsEntryRepository
import de.sambalmueslie.games.hll.tool.rcon.Slots
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class SlotMonitor(
    private val mapMonitor: MapMonitor,
    private val statsRepository: ServerSlotStatsEntryRepository
) : ServerMonitoringProcessor {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SlotMonitor::class.java)
    }

    private var currentSlots = mutableMapOf<Long, Slots>()

    private val currentSlotCache: LoadingCache<Long, ServerSlotStatsEntry> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .build { serverId -> statsRepository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    override fun runCycle(client: ServerClient) {
        if (!client.isSlotTrackingEnabled()) return

        val slots = client.getSlots()
        val current = currentSlotCache[client.id]
        val map = mapMonitor.getCurrentMap(client.id) ?: return
        if (!hasChanged(current, slots, map)) return
        handleSlotsChange(client, slots, map)
    }

    private fun hasChanged(current: ServerSlotStatsEntry?, slots: Slots, map: ServerMapData): Boolean {
        if (current == null) return true
        if (current.mapId != map.id) return true
        if (current.active != slots.active) return true
        return false
    }

    private fun handleSlotsChange(client: ServerClient, slots: Slots, map: ServerMapData) {
        if (!slots.isValid() || slots.active <= 0) return
        logger.info("Handle slot change from ${currentSlots[client.id]} -> $slots")
        currentSlots[client.id] = slots
        statsRepository.save(ServerSlotStatsEntry(0, slots.active, slots.available, map.id, client.id))
    }

}
