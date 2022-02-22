package de.sambalmueslie.games.hll.tool.monitor.map.api

import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance

interface MapStatsChangeListener {
    fun handleMapStatsChanged(instance: ServerInstance, data: MapChangeEventStats)
}
