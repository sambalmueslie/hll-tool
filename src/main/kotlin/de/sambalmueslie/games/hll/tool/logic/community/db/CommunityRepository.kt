package de.sambalmueslie.games.hll.tool.logic.community.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface CommunityRepository : PageableRepository<CommunityData, Long> {

    fun findByName(name: String): List<CommunityData>

}
