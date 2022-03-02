package de.sambalmueslie.games.hll.tool.logic.server.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ServerConnectionRepository : PageableRepository<ServerConnectionData, Long> {

    fun findByServerId(serverId: Long): List<ServerConnectionData>
    fun deleteByServerId(serverId: Long)
}
