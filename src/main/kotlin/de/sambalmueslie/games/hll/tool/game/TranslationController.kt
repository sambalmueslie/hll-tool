package de.sambalmueslie.games.hll.tool.game


import de.sambalmueslie.games.hll.tool.common.CrudAPI
import de.sambalmueslie.games.hll.tool.game.api.Translation
import de.sambalmueslie.games.hll.tool.game.api.TranslationChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication

@Controller("api/game/translation")
class TranslationController(private val service: TranslationService) : CrudAPI<Translation, TranslationChangeRequest> {

    @Get()
    override fun getAll(authentication: Authentication, pageable: Pageable) = service.getAll(authentication, pageable)

    @Get("{objId}")
    override fun get(authentication: Authentication, objId: Long) = service.get(authentication, objId)

    @Delete("{objId}")
    override fun delete(authentication: Authentication, objId: Long) = service.delete(authentication, objId)

    @Put("{objId}")
    override fun update(authentication: Authentication, objId: Long, request: TranslationChangeRequest) = service.update(authentication, objId, request)

    @Post()
    override fun create(authentication: Authentication, request: TranslationChangeRequest) = service.create(authentication, request)


}
