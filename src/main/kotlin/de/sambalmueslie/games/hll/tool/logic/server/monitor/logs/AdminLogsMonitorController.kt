package de.sambalmueslie.games.hll.tool.logic.server.monitor.logs


import io.micronaut.http.annotation.Controller

@Controller("api/logic/server/monitor/logs")
class AdminLogsMonitorController(private val service: AdminLogsMonitor) {

}
