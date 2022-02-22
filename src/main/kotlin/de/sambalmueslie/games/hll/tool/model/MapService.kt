package de.sambalmueslie.games.hll.tool.model


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.model.api.Map
import de.sambalmueslie.games.hll.tool.model.db.MapData
import de.sambalmueslie.games.hll.tool.model.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime

@Singleton
class MapService(
    private val repository: MapRepository
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapService::class.java)
    }

    private val currentMapCache: LoadingCache<Long, MapData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .build { serverId -> repository.findFirst1ByServerIdOrderByTimestampDesc(serverId) }

    fun getCurrentMap(serverId: Long): Map? = currentMapCache[serverId]

    fun setCurrentMap(instance: ServerInstance, name: String): Map {
        val timestamp = LocalDateTime.now()
        TODO("Not yet implemented")
    }

}
