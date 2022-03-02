//package de.sambalmueslie.games.hll.tool.game
//
//
//import com.github.benmanes.caffeine.cache.Caffeine
//import com.github.benmanes.caffeine.cache.LoadingCache
//import de.sambalmueslie.games.hll.tool.common.BaseCrudService
//import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
//import de.sambalmueslie.games.hll.tool.game.api.TranslationEntry
//import de.sambalmueslie.games.hll.tool.game.api.TranslationEntryChangeRequest
//import de.sambalmueslie.games.hll.tool.game.db.TranslationEntryData
//import de.sambalmueslie.games.hll.tool.game.db.TranslationEntryRepository
//import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
//import io.micronaut.security.authentication.Authentication
//import jakarta.inject.Singleton
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import java.time.Duration
//
//@Singleton
//class TranslationEntryService(
//    private val repository: TranslationEntryRepository,
//    private val translationService: TranslationService
//) : BaseCrudService<TranslationEntry, TranslationEntryChangeRequest, TranslationEntryData>(repository, logger) {
//
//    companion object {
//        val logger: Logger = LoggerFactory.getLogger(TranslationEntryService::class.java)
//    }
//
//    override fun convert(data: TranslationEntryData) = data.convert(EmptyConvertContent())
//
//    override fun create(auth: Authentication, request: TranslationEntryChangeRequest): TranslationEntry? {
//        if (!request.valid()) return null
//
//        val translation = translationService.get(request.translationId) ?: return null
//        val existing = repository.findByTranslationIdAndKey(translation.id, request.key)
//        if (existing != null) return update(auth, existing, request)
//
//        val data = TranslationEntryData.create(translation, request)
//        return convert(repository.save(data))
//    }
//
//    override fun update(auth: Authentication, objId: Long, request: TranslationEntryChangeRequest): TranslationEntry? {
//        val data = repository.findByIdOrNull(objId) ?: return create(auth, request)
//        return update(auth, data, request)
//    }
//
//    override fun update(auth: Authentication, obj: TranslationEntryData, request: TranslationEntryChangeRequest): TranslationEntry? {
//        if (!request.valid()) return null
//        val translation = translationService.get(request.translationId) ?: return null
//        obj.update(request)
//        obj.translationId = translation.id
//        return convert(repository.update(obj))
//    }
//
//    private val cache: LoadingCache<LangStringCacheKey, TranslationEntry> = Caffeine.newBuilder()
//        .maximumSize(1000)
//        .expireAfterAccess(Duration.ofMinutes(30))
//        .build { key -> repository.findByKeyAndLanguage(key.key, key.lang)?.let { convert(it) } }
//
//    fun getTranslation(key: String, lang: String): TranslationEntry? {
//        return cache[LangStringCacheKey(key, lang)]
//    }
//
//
//}
