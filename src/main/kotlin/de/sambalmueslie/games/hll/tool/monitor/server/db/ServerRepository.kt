package de.sambalmueslie.games.hll.tool.monitor.server.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ServerRepository : PageableRepository<ServerData, Long> {
    fun findByName(name: String): List<ServerData>
    fun findByHost(host: String): ServerData?
}
