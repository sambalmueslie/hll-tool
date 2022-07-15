package de.sambalmueslie.games.hll.tool.game


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.common.BaseCrudService
import de.sambalmueslie.games.hll.tool.game.api.Translation
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntry
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntryChangeRequest
import de.sambalmueslie.games.hll.tool.game.db.TranslationEntryData
import de.sambalmueslie.games.hll.tool.game.db.TranslationEntryRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class TranslationEntryService(
    listeners: Set<TranslationEntryChangeListener>,
    private val repository: TranslationEntryRepository,
    private val translationService: TranslationService
) : BaseAuthCrudService<TranslationEntry, TranslationEntryChangeRequest, TranslationEntryData>(listeners, repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TranslationEntryService::class.java)
    }

    override fun convert(request: TranslationEntryChangeRequest): TranslationEntryData {
        return TranslationEntryData.create(request)
    }

    override fun validate(request: TranslationEntryChangeRequest): Boolean {
        if (!request.valid()) return false
        val translation = translationService.get(request.translationId) ?: return false
        repository.findByTranslationIdAndKey(translation.id, request.key) ?: return false
        return true
    }

    private val cache: LoadingCache<LangStringCacheKey, TranslationEntry> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(30))
        .build { key -> repository.findByKeyAndLanguage(key.key, key.lang)?.convert() }

    fun getTranslation(key: String, lang: String): TranslationEntry? {
        return cache[LangStringCacheKey(key, lang)]
    }

    override fun get(auth: Authentication, objId: Long): TranslationEntry? {
        // TODO check auth
        return get(objId)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<TranslationEntry> {
        // TODO check auth
        return getAll(pageable)
    }
}
