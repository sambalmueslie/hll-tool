//package de.sambalmueslie.games.hll.tool.game
//
//
//import de.sambalmueslie.games.hll.tool.common.BaseCrudService
//import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
//import de.sambalmueslie.games.hll.tool.game.api.Translation
//import de.sambalmueslie.games.hll.tool.game.api.TranslationChangeRequest
//import de.sambalmueslie.games.hll.tool.game.db.TranslationData
//import de.sambalmueslie.games.hll.tool.game.db.TranslationRepository
//import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
//import io.micronaut.security.authentication.Authentication
//import jakarta.inject.Singleton
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@Singleton
//class TranslationService(private val repository: TranslationRepository) : BaseCrudService<Translation, TranslationChangeRequest, TranslationData>(repository, logger) {
//
//    companion object {
//        val logger: Logger = LoggerFactory.getLogger(TranslationService::class.java)
//    }
//
//    override fun convert(data: TranslationData) = data.convert(EmptyConvertContent())
//
//    override fun create(auth: Authentication, request: TranslationChangeRequest): Translation? {
//        if (!request.valid()) return null
//        val existing = repository.findByLang(request.lang)
//        if (existing != null) return update(auth, existing, request)
//        return convert(repository.save(TranslationData.create(request)))
//    }
//
//    override fun update(auth: Authentication, objId: Long, request: TranslationChangeRequest): Translation? {
//        val data = repository.findByIdOrNull(objId) ?: return create(auth, request)
//        return update(auth, data, request)
//    }
//
//    override fun update(auth: Authentication, obj: TranslationData, request: TranslationChangeRequest): Translation? {
//        if (!request.valid()) return null
//        obj.update(request)
//        return convert(repository.update(obj))
//    }
//
//
//}
