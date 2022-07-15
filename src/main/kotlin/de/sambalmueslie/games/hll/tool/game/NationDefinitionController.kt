package de.sambalmueslie.games.hll.tool.game


import de.sambalmueslie.games.hll.tool.common.CrudAPI
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import de.sambalmueslie.games.hll.tool.game.api.NationDefinitionChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication

@Controller("api/game/nation")
class NationDefinitionController(private val service: NationDefinitionService) : CrudAPI<NationDefinition, NationDefinitionChangeRequest> {

    @Get()
    override fun getAll(authentication: Authentication, pageable: Pageable) = service.getAll(authentication, pageable)

    @Get("{objId}")
    override fun get(authentication: Authentication, objId: Long) = service.get(authentication, objId)

    @Delete("{objId}")
    override fun delete(authentication: Authentication, objId: Long) = service.delete(authentication, objId)

    @Put("{objId}")
    override fun update(authentication: Authentication, objId: Long, request: NationDefinitionChangeRequest) = service.update(authentication, objId, request)

    @Post()
    override fun create(authentication: Authentication, request: NationDefinitionChangeRequest) = service.create(authentication, request)


}
