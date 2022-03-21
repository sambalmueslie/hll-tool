package de.sambalmueslie.games.hll.tool.logic.server.monitor.map


import io.micronaut.http.annotation.Controller

@Controller("api/logic/server/monitor/map")
class MapMonitorController(private val service: MapMonitor)
