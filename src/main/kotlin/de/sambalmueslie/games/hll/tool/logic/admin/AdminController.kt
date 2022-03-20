package de.sambalmueslie.games.hll.tool.logic.admin


import io.micronaut.http.annotation.Controller

@Controller("api/logic/admin")
class AdminController(private val service: AdminService) {


}
