package de.sambalmueslie.games.hll.tool.game


import de.sambalmueslie.games.hll.tool.common.CrudAPI
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntry
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntryChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication

@Controller("api/game/translation/entry")
class TranslationEntryController(private val service: TranslationEntryService) : CrudAPI<TranslationEntry, TranslationEntryChangeRequest> {

    @Get()
    override fun getAll(authentication: Authentication, pageable: Pageable) = service.getAll(authentication, pageable)

    @Get("{objId}")
    override fun get(authentication: Authentication, objId: Long) = service.get(authentication, objId)

    @Delete("{objId}")
    override fun delete(authentication: Authentication, objId: Long) = service.delete(authentication, objId)

    @Put("{objId}")
    override fun update(authentication: Authentication, objId: Long, request: TranslationEntryChangeRequest) = service.update(authentication, objId, request)

    @Post()
    override fun create(authentication: Authentication, request: TranslationEntryChangeRequest) = service.create(authentication, request)


}
