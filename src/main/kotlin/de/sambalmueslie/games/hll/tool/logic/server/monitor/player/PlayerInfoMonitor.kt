package de.sambalmueslie.games.hll.tool.logic.server.monitor.player


import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitoringProcessor
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PlayerInfoMonitor : ServerMonitoringProcessor {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PlayerInfoMonitor::class.java)
    }

    override fun runCycle(client: ServerClient) {
        if (!client.isPlayerTrackingEnabled()) return
        TODO("Not yet implemented")
    }


}
