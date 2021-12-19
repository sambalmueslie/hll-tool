package de.sambalmueslie.games.hll.tool.monitor.logs


import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstanceProcessor
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class LogsProcessor() : ServerInstanceProcessor {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(LogsProcessor::class.java)
    }

    override fun runCycle(instance: ServerInstance) {
//        TODO("Not yet implemented")
    }


}
