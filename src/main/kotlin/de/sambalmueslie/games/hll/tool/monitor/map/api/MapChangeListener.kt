package de.sambalmueslie.games.hll.tool.monitor.map.api

import de.sambalmueslie.games.hll.tool.model.api.Map
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance

interface MapChangeListener {
    fun handleMapChanged(instance: ServerInstance, map: Map)
}
