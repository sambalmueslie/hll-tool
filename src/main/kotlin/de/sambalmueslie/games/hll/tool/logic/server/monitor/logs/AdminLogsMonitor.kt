package de.sambalmueslie.games.hll.tool.logic.server.monitor.logs


import de.sambalmueslie.games.hll.tool.logic.server.monitor.ServerClient
import de.sambalmueslie.games.hll.tool.logic.server.monitor.api.ServerMonitoringProcessor
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AdminLogsMonitor : ServerMonitoringProcessor {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AdminLogsMonitor::class.java)
    }

    override fun runCycle(client: ServerClient) {
        if (!client.isLogTrackingEnabled()) return
        TODO("Not yet implemented")
    }


}
