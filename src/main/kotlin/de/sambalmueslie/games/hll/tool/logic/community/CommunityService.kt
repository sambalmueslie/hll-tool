package de.sambalmueslie.games.hll.tool.logic.community


import de.sambalmueslie.games.hll.tool.common.BaseAuthCrudService
import de.sambalmueslie.games.hll.tool.logic.community.api.Community
import de.sambalmueslie.games.hll.tool.logic.community.api.CommunityChangeListener
import de.sambalmueslie.games.hll.tool.logic.community.api.CommunityChangeRequest
import de.sambalmueslie.games.hll.tool.logic.community.db.CommunityData
import de.sambalmueslie.games.hll.tool.logic.community.db.CommunityRepository
import de.sambalmueslie.games.hll.tool.util.findByIdOrNull
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class CommunityService(
    private val repository: CommunityRepository,
    listeners: Set<CommunityChangeListener>
) : BaseAuthCrudService<Community, CommunityChangeRequest, CommunityData>(listeners, repository, logger) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommunityService::class.java)
    }

    override fun getAll(auth: Authentication, pageable: Pageable): Page<Community> {
        // TODO check auth
        return repository.findAll(pageable).map { it.convert() }
    }

    override fun get(auth: Authentication, objId: Long): Community? {
        // TODO check auth
        return repository.findByIdOrNull(objId)
    }

    override fun create(request: CommunityChangeRequest): CommunityData {
        return CommunityData.create(request)
    }

    override fun validate(request: CommunityChangeRequest): Boolean {
        if (!request.valid()) return false
        val existing = repository.findByName(request.name)
        if (existing.isNotEmpty()) return false

        return true
    }


}
