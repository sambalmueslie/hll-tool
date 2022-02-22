package de.sambalmueslie.games.hll.tool.monitor.map


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEventStats
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapStatsChangeListener
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapStatsData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapStatsRepository
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
class MapStatsProcessor(
    private val mapRepository: MapRepository,
    private val repository: MapStatsRepository,
    private val listener: Set<MapStatsChangeListener>
) : MapChangeListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapStatsProcessor::class.java)
        private val maxDuration = Duration.ofMinutes(95)
    }

    private val lastEventCache: LoadingCache<Long, MapData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(90))
        .build { serverId -> mapRepository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    override fun handleMapChanged(instance: ServerInstance, data: MapData) {
        val serverId = instance.id
        val lastEvent = lastEventCache[serverId] ?: return

        val duration = Duration.between(lastEvent.timestamp, data.timestamp)
        if (duration > maxDuration) return logger.warn("Ignore event, cause max duration is exceed $duration")
        repository.save(MapStatsData(0, data.id, serverId, duration))

        val statsEvent = MapChangeEventStats(data.id, data.name, LocalDateTime.now(ZoneOffset.UTC), duration)
        listener.forEachWithTryCatch { it.handleMapStatsChanged(instance, statsEvent) }
    }

}
