package de.sambalmueslie.games.hll.tool.monitor.map.api

import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance

interface MapChangeListener {
    fun handleMapChanged(instance: ServerInstance, data: MapData)
}
