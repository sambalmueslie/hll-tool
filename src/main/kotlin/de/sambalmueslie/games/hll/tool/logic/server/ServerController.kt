package de.sambalmueslie.games.hll.tool.logic.server


import de.sambalmueslie.games.hll.tool.common.CrudAPI
import de.sambalmueslie.games.hll.tool.logic.server.api.Server
import de.sambalmueslie.games.hll.tool.logic.server.api.ServerChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication

@Controller("api/logic/server")
class ServerController(private val service: ServerService) : CrudAPI<Server, ServerChangeRequest> {

    @Get()
    override fun getAll(authentication: Authentication, pageable: Pageable) = service.getAll(authentication, pageable)

    @Get("{objId}")
    override fun get(authentication: Authentication, objId: Long) = service.get(authentication, objId)

    @Post()
    override fun create(authentication: Authentication, request: ServerChangeRequest) = service.create(authentication, request)

    @Put("{objId}")
    override fun update(authentication: Authentication, objId: Long, request: ServerChangeRequest) = service.update(authentication, objId, request)

    @Delete("{objId}")
    override fun delete(authentication: Authentication, objId: Long) = service.delete(authentication, objId)


}
