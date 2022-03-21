package de.sambalmueslie.games.hll.tool.logic.server.monitor.slot


import io.micronaut.http.annotation.Controller

@Controller("api/logic/server/monitor/slot")
class SlotMonitorController(private val service: SlotMonitor)
