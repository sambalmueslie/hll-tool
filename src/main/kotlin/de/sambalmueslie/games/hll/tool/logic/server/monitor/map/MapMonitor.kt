package de.sambalmueslie.games.hll.tool.logic.server.monitor.map


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitoringProcessor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api.ServerMapChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapData
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapRepository
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapStatsEntry
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.db.ServerMapStatsEntryRepository
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class MapMonitor(
    private val repository: ServerMapRepository,
    private val statsRepository: ServerMapStatsEntryRepository,
    private val listeners: MutableSet<ServerMapChangeListener>
) : ServerMonitoringProcessor {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MapMonitor::class.java)
        private const val CYCLE_COUNT_LIMIT = 5
        private val maxDuration = Duration.ofMinutes(95)
    }

    private val currentMapCache: LoadingCache<Long, ServerMapData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .build { serverId -> repository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }


    fun getCurrentMap(serverId: Long) = currentMapCache[serverId]

    fun register(listener: ServerMapChangeListener){
        listeners.add(listener)
    }

    fun unregister(listener: ServerMapChangeListener){
        listeners.remove(listener)
    }

    private var cycleCount = 0
    override fun runCycle(client: ServerClient) {
        if (!client.isMapTrackingEnabled()) return
        cycleCount++
        if (cycleCount < CYCLE_COUNT_LIMIT) return
        updateServerMap(client)
        cycleCount = 0
    }

    private fun updateServerMap(client: ServerClient) {
        val map = client.getMap()
        val current = currentMapCache[client.id]
        if (current?.name == map) return
        handleMapChange(client, map, current)
    }

    private fun handleMapChange(client: ServerClient, name: String, current: ServerMapData?) {
        if (!client.mapsInRotation.contains(name)) {
            logger.error("Invalid map name '$name'")
            return
        }
        logger.info("Handle map change from $current -> $name")
        val data = repository.save(ServerMapData(0, name, client.id))

        updateStats(client, current, data)
        listeners.forEachWithTryCatch { it.handleMapChanged(client, data) }
    }

    private fun updateStats(client: ServerClient, last: ServerMapData?, data: ServerMapData) {
        if (last == null) return logger.warn("Cannot create server stats for ${client.id} (${client.name}) cause no last value is present")
        val duration = Duration.between(last.timestamp, data.timestamp)
        if (duration > maxDuration) return logger.warn("Ignore event, cause max duration is exceed $duration")
        statsRepository.save(ServerMapStatsEntry(0, data.id, client.id, duration))
    }

}
