package de.sambalmueslie.games.hll.tool.game


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.common.BaseCrudService
import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
import de.sambalmueslie.games.hll.tool.game.api.NationDefinition
import de.sambalmueslie.games.hll.tool.game.api.NationDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.NationInfo
import de.sambalmueslie.games.hll.tool.game.db.NationDefinitionData
import de.sambalmueslie.games.hll.tool.game.db.NationDefinitionRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class NationService(
    private val repository: NationDefinitionRepository,
    private val translationService: TranslationEntryService
) : BaseCrudService<NationDefinition, NationDefinitionChangeRequest, NationDefinitionData>(repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(NationService::class.java)
    }

    override fun convert(data: NationDefinitionData) = data.convert(EmptyConvertContent())

    override fun create(auth: Authentication, request: NationDefinitionChangeRequest): NationDefinition? {
        if (!request.valid()) return null
        val existing = repository.findByKey(request.key)
        if (existing != null) return update(auth, existing, request)
        return convert(repository.save(NationDefinitionData.create(request)))
    }

    override fun update(auth: Authentication, objId: Long, request: NationDefinitionChangeRequest): NationDefinition? {
        val data = repository.findByIdOrNull(objId) ?: return create(auth, request)
        return update(auth, data, request)
    }

    override fun update(auth: Authentication, obj: NationDefinitionData, request: NationDefinitionChangeRequest): NationDefinition? {
        if (!request.valid()) return null
        obj.update(request)
        return convert(repository.update(obj))
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


}
