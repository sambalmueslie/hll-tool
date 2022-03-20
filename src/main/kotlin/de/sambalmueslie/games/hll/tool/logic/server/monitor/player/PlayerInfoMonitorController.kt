package de.sambalmueslie.games.hll.tool.logic.server.monitor.player


import io.micronaut.http.annotation.Controller

@Controller("api/logic/server/monitor/player")
class PlayerInfoMonitorController(private val service: PlayerInfoMonitor) {

}
