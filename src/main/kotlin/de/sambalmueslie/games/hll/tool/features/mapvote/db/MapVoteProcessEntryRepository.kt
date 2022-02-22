package de.sambalmueslie.games.hll.tool.features.mapvote.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface MapVoteProcessEntryRepository : PageableRepository<MapVoteProcessEntryData, Long> {
    fun findByVoteProcessId(voteProcessId: Long): List<MapVoteProcessEntryData>
    fun findByVoteProcessId(voteProcessId: Long, pageable: Pageable): Page<MapVoteProcessEntryData>
}
