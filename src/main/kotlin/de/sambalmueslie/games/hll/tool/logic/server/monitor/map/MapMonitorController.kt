package de.sambalmueslie.games.hll.tool.logic.server.monitor.map


import io.micronaut.http.annotation.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("api/logic/server/monitor/map")
class MapMonitorController(private val service: MapMonitor) {

}
