package de.sambalmueslie.games.hll.tool.monitor.slot.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface SlotChangeRepository : PageableRepository<SlotData, Long> {
    fun findFirst1ByServerIdOrderByTimestampDesc(serverId: Long): SlotData?
}
