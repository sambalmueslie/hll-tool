package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntry
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntryChangeRequest
import jakarta.persistence.*

@Entity(name = "TranslationEntry")
@Table(name = "game_translation_entry")
data class TranslationEntryData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false)
    var translationId: Long = 0,
    @Column(nullable = false)
    var key: String = "",
    @Column(nullable = false)
    var value: String = ""
) : DataObject<TranslationEntry, TranslationEntryChangeRequest> {

    companion object {
        fun create(request: TranslationEntryChangeRequest) =
            TranslationEntryData(0, request.translationId, request.key, request.value)
    }

    override fun convert() = TranslationEntry(id, key, value)

    override fun update(request: TranslationEntryChangeRequest) {
        key = request.key
        value = request.value
    }
}

