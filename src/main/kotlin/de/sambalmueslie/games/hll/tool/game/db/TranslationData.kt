package de.sambalmueslie.games.hll.tool.game.db

import de.sambalmueslie.games.hll.tool.common.DataObject
import de.sambalmueslie.games.hll.tool.common.EmptyConvertContent
import de.sambalmueslie.games.hll.tool.game.api.Translation
import de.sambalmueslie.games.hll.tool.game.api.TranslationChangeRequest
import javax.persistence.*

@Entity(name = "Translation")
@Table(name = "game_translation")
data class TranslationData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long = 0,
    @Column(nullable = false, unique = true)
    var lang: String
) : DataObject<Translation, TranslationChangeRequest, EmptyConvertContent> {

    companion object {
        fun create(request: TranslationChangeRequest) = TranslationData(0, request.lang)
    }

    override fun convert(content: EmptyConvertContent) = Translation(id, lang)

    override fun update(request: TranslationChangeRequest) {
        lang = request.lang
    }
}

