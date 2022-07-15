package de.sambalmueslie.games.hll.tool.logic.server.info


import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.authentication.Authentication

@Controller("api/logic/server/info")
class ServerInfoController(private val service: ServerInfoService) {
    @Get("{objId}")
    fun getInfo(auth: Authentication, @PathVariable objId: Long) = service.getInfo(auth, objId)

    @Get("{objId}/maps")
    fun getMaps(auth: Authentication, @PathVariable objId: Long) = service.getMaps(auth, objId)
}
