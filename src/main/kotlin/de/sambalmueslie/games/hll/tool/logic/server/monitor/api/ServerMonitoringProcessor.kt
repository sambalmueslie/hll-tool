package de.sambalmueslie.games.hll.tool.logic.server.monitor.api

import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient

interface ServerMonitoringProcessor {
    fun runCycle(client: ServerClient)
}
