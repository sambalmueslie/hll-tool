package de.sambalmueslie.games.hll.tool.monitor.map


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEvent
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEventStats
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapStatsData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapStatsRepository
import de.sambalmueslie.games.hll.tool.monitor.map.kafka.MapChangeEventStatsProducer
import de.sambalmueslie.games.hll.tool.monitor.server.ServerProcessor
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
class MapStatsProcessor(
    private val serverProcessor: ServerProcessor,
    private val statsProducer: MapChangeEventStatsProducer,
    private val mapRepository: MapRepository,
    private val repository: MapStatsRepository
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapStatsProcessor::class.java)
        private val maxDuration = Duration.ofMinutes(95)
    }

    private val lastEventCache: LoadingCache<Long, MapData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(90))
        .build { serverId -> mapRepository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    fun mapChangeEvent(serverId: Long, event: MapChangeEvent) {
        val instance = serverProcessor.getInstanceByServerId(serverId) ?: return
        val lastEvent = lastEventCache[instance.id] ?: return

        val duration = Duration.between(lastEvent.timestamp, event.timestamp)
        if (duration > maxDuration) return logger.warn("Ignore event, cause max duration is exceed $duration")
        repository.save(MapStatsData(0, event.id, serverId, duration))

        val statsEvent = MapChangeEventStats(event.id, event.map, LocalDateTime.now(ZoneOffset.UTC), duration)
        statsProducer.sendEvent(serverId, statsEvent)
    }

}
