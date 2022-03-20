package de.sambalmueslie.games.hll.tool.logic.server.monitor.map.api

import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient

interface ServerMapChangeListener {
    fun handleMapChanged(client: ServerClient, map: ServerMap)
}
