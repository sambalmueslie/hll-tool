package de.sambalmueslie.games.hll.tool.logic.community


import de.sambalmueslie.games.hll.tool.common.CrudAPI
import de.sambalmueslie.games.hll.tool.logic.community.api.Community
import de.sambalmueslie.games.hll.tool.logic.community.api.CommunityChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication

@Controller("api/logic/community")
class CommunityController(private val service: CommunityService) : CrudAPI<Community, CommunityChangeRequest> {
    @Get()
    override fun getAll(authentication: Authentication, pageable: Pageable) = service.getAll(authentication, pageable)

    @Get("{objId}")
    override fun get(authentication: Authentication, objId: Long) = service.get(authentication, objId)

    @Post()
    override fun create(authentication: Authentication, request: CommunityChangeRequest) = service.create(authentication, request)

    @Put("{objId}")
    override fun update(authentication: Authentication, objId: Long, request: CommunityChangeRequest) = service.update(authentication, objId, request)

    @Delete("{objId}")
    override fun delete(authentication: Authentication, objId: Long) = service.delete(authentication, objId)

}
