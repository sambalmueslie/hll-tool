package de.sambalmueslie.games.hll.tool.game


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.games.hll.tool.common.BaseCrudService
import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
import de.sambalmueslie.games.hll.tool.game.api.MapDefinition
import de.sambalmueslie.games.hll.tool.game.api.MapDefinitionChangeRequest
import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import de.sambalmueslie.games.hll.tool.game.db.MapDefinitionData
import de.sambalmueslie.games.hll.tool.game.db.MapDefinitionRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

@Singleton
class MapService(
    private val repository: MapDefinitionRepository,
    private val nationService: NationService,
    private val translationService: TranslationEntryService
) : BaseCrudService<MapDefinition, MapDefinitionChangeRequest, MapDefinitionData>(repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapService::class.java)
    }

    override fun convert(data: MapDefinitionData) = data.convert(EmptyConvertContent())

    override fun create(auth: Authentication, request: MapDefinitionChangeRequest): MapDefinition? {
        if (!request.valid()) return null
        val attacker = nationService.get(request.attackerId) ?: return null
        val defender = nationService.get(request.defenderId) ?: return null

        val existing = repository.findByKey(request.key)
        if (existing != null) return update(auth, existing, request)
        return convert(repository.save(MapDefinitionData.create(attacker, defender, request)))
    }

    override fun update(auth: Authentication, objId: Long, request: MapDefinitionChangeRequest): MapDefinition? {
        val data = repository.findByIdOrNull(objId) ?: return create(auth, request)
        return update(auth, data, request)
    }

    override fun update(auth: Authentication, obj: MapDefinitionData, request: MapDefinitionChangeRequest): MapDefinition? {
        if (!request.valid()) return null
        val attacker = nationService.get(request.attackerId) ?: return null
        val defender = nationService.get(request.defenderId) ?: return null
        obj.update(request)
        obj.attackerId = attacker.id
        obj.defenderId = defender.id
        return convert(repository.update(obj))
    }

    private val idCache: LoadingCache<LangObjCacheKey, MapInfo> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(Duration.ofMinutes(30))
        .build { cacheKey ->
            val definition = getData(cacheKey.objId) ?: return@build null
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
            getInfo(definition, cacheKey.lang)
        }

    private fun getInfo(definition: MapDefinitionData, lang: String): MapInfo? {
        val key = "Map.${definition.key}"
        val translation = translationService.getTranslation(key, lang) ?: translationService.getTranslation(key, "en") ?: return null
        val attacker = nationService.getInfo(definition.attackerId, lang) ?: return null
        val defender = nationService.getInfo(definition.defenderId, lang) ?: return null

        return MapInfo(definition.id, definition.key, translation.value, definition.type, attacker, defender)
    }

    fun getInfo(key: String, lang: String): MapInfo? {
        return keyCache[LangStringCacheKey(key, lang)]
    }

}
