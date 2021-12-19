package de.sambalmueslie.games.hll.tool.monitor.map


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeEvent
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.map.kafka.MapChangeEventProducer
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.monitor.server.ServerProcessor
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
open class MapChangeEventProcessor(
    private val serverProcessor: ServerProcessor,
    private val eventProducer: MapChangeEventProducer,
    private val repository: MapRepository
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapChangeEventProcessor::class.java)
    }

    private val currentMapCache: LoadingCache<Long, MapData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .build { serverId -> repository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    fun getCurrentMap(serverId: Long) = currentMapCache[serverId]

    @Scheduled(fixedDelay = "10s")
    open fun updateMap() {
        serverProcessor.instances.filter { it.isMapTrackingEnabled() }.forEach { updateServerMap(it) }
    }

    private fun updateServerMap(instance: ServerInstance) {
        val map = instance.getMap()
        val current = currentMapCache[instance.id]
        if (current?.name == map) return
        handleMapChange(instance, map, current)
    }

    private fun handleMapChange(instance: ServerInstance, map: String, current: MapData?) {
        if (!instance.mapsInRotation.contains(map)) {
            logger.error("Invalid map name '$map'")
            return
        }
        logger.info("Handle map change from $current -> $map")
        val timestamp = LocalDateTime.now(ZoneOffset.UTC)
        val data = repository.save(MapData(0, map, instance.id, timestamp))
        currentMapCache.put(instance.id, data)

        eventProducer.sendEvent(instance.id, MapChangeEvent(data.id, data.name, timestamp))
    }
}
