package de.sambalmueslie.games.hll.tool.game


import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import de.sambalmueslie.games.hll.tool.game.api.Translation
import de.sambalmueslie.games.hll.tool.game.api.TranslationChangeRequest
import de.sambalmueslie.games.hll.tool.game.db.TranslationData
import de.sambalmueslie.games.hll.tool.game.db.TranslationRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TranslationService(
    listeners: Set<TranslationChangeListener>, private val repository: TranslationRepository
) : BaseAuthCrudService<Translation, TranslationChangeRequest, TranslationData>(listeners, repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TranslationService::class.java)
    }

    override fun convert(request: TranslationChangeRequest): TranslationData {
        return TranslationData.create(request)
    }

    override fun validate(request: TranslationChangeRequest): Boolean {
        if (!request.valid()) return false
        repository.findByLang(request.lang) ?: return false
        return true
    }

    override fun get(auth: Authentication, objId: Long): Translation? {
        // TODO check auth
        return get(objId)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<Translation> {
        // TODO check auth
        return getAll(pageable)
    }
}
