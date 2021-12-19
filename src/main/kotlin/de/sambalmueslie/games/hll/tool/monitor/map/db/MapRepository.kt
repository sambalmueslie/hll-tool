package de.sambalmueslie.games.hll.tool.monitor.map.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface MapRepository : PageableRepository<MapData, Long> {
    fun findFirst1ByServerIdOrderByTimestampDesc(serverId: Long): MapData?
    fun findFirst1ByServerIdAndNameOrderByTimestampDesc(serverId: Long, name: String): MapData?
}
