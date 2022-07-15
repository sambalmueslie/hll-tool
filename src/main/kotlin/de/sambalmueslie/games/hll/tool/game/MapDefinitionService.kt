package de.sambalmueslie.games.hll.tool.game


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.common.CrudService
import de.sambalmueslie.games.hll.tool.game.api.MapDefinition
import de.sambalmueslie.games.hll.tool.game.api.MapDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import de.sambalmueslie.games.hll.tool.game.db.MapDefinitionData
import de.sambalmueslie.games.hll.tool.game.db.MapDefinitionRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class MapDefinitionService(
    listeners: Set<MapDefinitionChangeListener>,
    private val repository: MapDefinitionRepository,
    private val nationService: NationDefinitionService,
    private val translationService: TranslationEntryService
) : BaseAuthCrudService<MapDefinition, MapDefinitionChangeRequest, MapDefinitionData>(listeners, repository, logger), CrudService<MapDefinition, MapDefinitionChangeRequest, MapDefinitionData> {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapDefinitionService::class.java)
    }

    override fun convert(request: MapDefinitionChangeRequest): MapDefinitionData {
        return MapDefinitionData.create(request)
    }

    override fun validate(request: MapDefinitionChangeRequest): Boolean {
        if (!request.valid()) return false
        nationService.get(request.attackerId) ?: return false
        nationService.get(request.defenderId) ?: return false
        repository.findByKey(request.key) ?: return false
        return true
    }


    private val idCache: LoadingCache<LangObjCacheKey, MapInfo> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(30))
        .build { cacheKey ->
            val definition = get(cacheKey.objId) ?: return@build null
            getInfo(definition, cacheKey.lang)
        }

    fun getInfo(objId: Long, lang: String): MapInfo? {
        return idCache[LangObjCacheKey(objId, lang)]
    }


    private val keyCache: LoadingCache<LangStringCacheKey, MapInfo> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(30))
        .build { cacheKey ->
            val definition = repository.findByKey(cacheKey.key) ?: return@build null
            getInfo(definition.convert(), cacheKey.lang)
        }

    private fun getInfo(definition: MapDefinition, lang: String): MapInfo? {
        val key = "Map.${definition.key}"
        val translation = translationService.getTranslation(key, lang) ?: translationService.getTranslation(key, "en") ?: return null
        val attacker = nationService.getInfo(definition.attackerId, lang) ?: return null
        val defender = nationService.getInfo(definition.defenderId, lang) ?: return null

        return MapInfo(definition.id, definition.key, translation.value, definition.type, attacker, defender)
    }

    fun getInfo(key: String, lang: String): MapInfo? {
        return keyCache[LangStringCacheKey(key, lang)]
    }

    override fun get(auth: Authentication, objId: Long): MapDefinition? {
        // TODO check auth
        return get(objId)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<MapDefinition> {
        // TODO check auth
        return getAll(pageable)
    }

}
