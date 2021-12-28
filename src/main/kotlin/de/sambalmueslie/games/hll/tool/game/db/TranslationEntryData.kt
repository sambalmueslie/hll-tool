package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
import de.sambalmueslie.games.hll.tool.game.api.Translation
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntry
import de.sambalmueslie.games.hll.tool.game.api.TranslationEntryChangeRequest
import javax.persistence.*

@Entity(name = "TranslationEntry")
@Table(name = "game_translation_entry")
data class TranslationEntryData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false)
    var translationId: Long,
    @Column(nullable = false)
    var key: String,
    @Column(nullable = false)
    var value: String
) : DataObject<TranslationEntry, TranslationEntryChangeRequest, EmptyConvertContent> {

    companion object {
        fun create(translation: Translation, request: TranslationEntryChangeRequest) =
            TranslationEntryData(0, translation.id, request.key, request.value)
    }

    override fun convert(content: EmptyConvertContent) = TranslationEntry(id, key, value)

    override fun update(request: TranslationEntryChangeRequest) {
        key = request.key
        value = request.value
    }
}

