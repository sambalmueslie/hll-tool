package de.sambalmueslie.games.hll.tool.logic.server.info


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.logic.server.ServerService
import de.sambalmueslie.games.hll.tool.logic.server.info.api.ServerInfo
import de.sambalmueslie.games.hll.tool.logic.server.info.db.ServerInfoData
import de.sambalmueslie.games.hll.tool.logic.server.info.db.ServerInfoRepository
import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerMonitorService
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.MapMonitor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api.ServerMap
import de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api.ServerMapChangeListener
import de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.SlotMonitor
import de.sambalmueslie.games.hll.tool.logic.server.monitor.slot.api.ServerSlotsChangeListener
import de.sambalmueslie.games.hll.tool.rcon.Slots
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class ServerInfoService(
    private val serverService: ServerService,
    private val infoRepository: ServerInfoRepository,
    private val monitorService: ServerMonitorService,
    private val mapMonitor: MapMonitor,
    private val slotMonitor: SlotMonitor
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ServerInfoService::class.java)
    }

    init {
        mapMonitor.register(object : ServerMapChangeListener {
            override fun handleMapChanged(client: ServerClient, map: ServerMap) {
                val data = getInfo(client)
                data.map = map.name
                infoRepository.update(data)
                cache.invalidate(client.id)
            }
        })

        slotMonitor.register(object : ServerSlotsChangeListener {
            override fun handleSlotsChanged(client: ServerClient, slots: Slots) {
                val data = getInfo(client)
                data.slotsActive = slots.active
                data.slotsAvailable = slots.available
                infoRepository.update(data)
                cache.invalidate(client.id)
            }
        })
    }

    private val cache: LoadingCache<Long, ServerInfoData> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(10))
        .build { serverId -> infoRepository.findByIdOrNull(serverId) }

    fun getInfo(authentication: Authentication, objId: Long): ServerInfo? {
        val server = serverService.get(authentication, objId) ?: return null
        val data = cache.get(server.id) ?: return null
        return data.convert(server)
    }


    private fun getInfo(client: ServerClient): ServerInfoData {
        val existing = infoRepository.findByIdOrNull(client.id)
        if (existing != null) return existing
        return infoRepository.save(ServerInfoData(client.id, client.name))
    }

    fun getMaps(authentication: Authentication, objId: Long): Set<String> {
        val server = serverService.get(authentication, objId) ?: return emptySet()
        val client = monitorService.getClient(server) ?: return emptySet()
        return client.mapsInRotation
    }
}
