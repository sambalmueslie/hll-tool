package de.sambalmueslie.games.hll.tool.logic.server.monitor

import io.micronaut.http.annotation.Controller


@Controller("api/logic/server/monitor")
class ServerMonitorController(private val service: ServerMonitorService)
