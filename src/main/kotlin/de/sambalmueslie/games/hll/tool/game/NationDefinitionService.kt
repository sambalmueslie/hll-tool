package de.sambalmueslie.games.hll.tool.game


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.common.CrudService
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import de.sambalmueslie.games.hll.tool.game.api.NationDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.NationInfo
import de.sambalmueslie.games.hll.tool.game.db.NationDefinitionData
import de.sambalmueslie.games.hll.tool.game.db.NationDefinitionRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class NationDefinitionService(
    listeners: Set<NationDefinitionChangeListener>,
    private val repository: NationDefinitionRepository,
    private val translationService: TranslationEntryService
) : BaseAuthCrudService<NationDefinition, NationDefinitionChangeRequest, NationDefinitionData>(listeners, repository, logger),
    CrudService<NationDefinition, NationDefinitionChangeRequest, NationDefinitionData> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(NationDefinitionService::class.java)
    }

    override fun convert(request: NationDefinitionChangeRequest): NationDefinitionData {
        return NationDefinitionData.create(request)
    }

    override fun validate(request: NationDefinitionChangeRequest): Boolean {
        if (!request.valid()) return false
        repository.findByKey(request.key) ?: return false
        return true
    }

    private val cache: LoadingCache<LangObjCacheKey, NationInfo> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(30))
        .build { cacheKey ->
            val definition = get(cacheKey.objId) ?: return@build null
            val key = "Nation.${definition.key}"
            val translation = translationService.getTranslation(key, cacheKey.lang) ?: translationService.getTranslation(key, "en") ?: return@build null

            NationInfo(definition.key, translation.value)
        }

    fun getInfo(objId: Long, lang: String): NationInfo? {
        return cache[LangObjCacheKey(objId, lang)]
    }

    override fun get(auth: Authentication, objId: Long): NationDefinition? {
        // TODO check auth
        return get(objId)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<NationDefinition> {
        // TODO check auth
        return getAll(pageable)
    }


}
