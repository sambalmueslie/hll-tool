package de.sambalmueslie.games.hll.tool.monitor.map.db

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
interface MapStatsRepository : PageableRepository<MapStatsData, Long> {
}
