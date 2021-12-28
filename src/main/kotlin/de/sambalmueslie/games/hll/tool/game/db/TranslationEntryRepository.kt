package de.sambalmueslie.games.hll.tool.game.db

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TranslationEntryRepository : PageableRepository<TranslationEntryData, Long> {
    fun findByTranslationId(translationId: Long): List<TranslationEntryData>
    fun findByTranslationId(translationId: Long, pageable: Pageable): Page<TranslationEntryData>

    fun findByTranslationIdAndKey(translationId: Long, key: String): TranslationEntryData?

    @Query("SELECT e.* from game_translation_entry e INNER JOIN game_translation t ON t.id = e.translation_id WHERE t.lang = :lang and e.key = :key")
    fun findByKeyAndLanguage(key: String, lang: String): TranslationEntryData?

}
