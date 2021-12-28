package de.sambalmueslie.games.hll.tool.monitor.server.db

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
interface ServerRepository : PageableRepository<ServerData, Long> {
    fun findByHost(host: String): ServerData?
}
