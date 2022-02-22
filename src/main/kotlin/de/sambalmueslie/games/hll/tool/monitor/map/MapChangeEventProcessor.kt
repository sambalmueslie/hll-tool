package de.sambalmueslie.games.hll.tool.monitor.map


import de.sambalmueslie.games.hll.tool.model.MapService
import de.sambalmueslie.games.hll.tool.model.api.Map
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstanceProcessor
import de.sambalmueslie.games.hll.tool.util.forEachWithTryCatch
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class MapChangeEventProcessor(
    private val mapService: MapService,
    private val listener: Set<MapChangeListener>
) : ServerInstanceProcessor {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapChangeEventProcessor::class.java)
        const val CYCLE_COUNT_LIMIT = 5
    }

    fun getCurrentMap(serverId: Long) = mapService.getCurrentMap(serverId)

    private var cycleCount = 0
    override fun runCycle(instance: ServerInstance) {
        if (!instance.isMapTrackingEnabled()) return
        cycleCount++
        if (cycleCount < CYCLE_COUNT_LIMIT) return
        updateServerMap(instance)
        cycleCount = 0
    }

    private fun updateServerMap(instance: ServerInstance) {
        val map = instance.getMap()
        val current = mapService.getCurrentMap(instance.id)
        if (current?.name == map) return
        handleMapChange(instance, map, current)
    }

    private fun handleMapChange(instance: ServerInstance, name: String, current: Map?) {
        if (!instance.mapsInRotation.contains(name)) {
            logger.error("Invalid map name '$name'")
            return
        }
        logger.info("Handle map change from $current -> $name")
        val data = mapService.setCurrentMap(instance, name)
        listener.forEachWithTryCatch { it.handleMapChanged(instance, data) }
    }
}
