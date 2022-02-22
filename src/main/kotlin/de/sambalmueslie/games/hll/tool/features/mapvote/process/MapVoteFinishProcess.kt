package de.sambalmueslie.games.hll.tool.features.mapvote.process


import de.sambalmueslie.games.hll.tool.features.mapvote.ServerMapVoting
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteResultData
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteResultRepository
import de.sambalmueslie.games.hll.tool.features.mapvote.discord.DiscordConnector
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class MapVoteFinishProcess(
    private val connector: DiscordConnector,
    private val resultRepository: MapVoteResultRepository
) {


    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteFinishProcess::class.java)
    }

    fun handleFinished(existing: ServerMapVoting) {
        val channelId = existing.message.channelId
        val messageId = existing.message.id
        connector.getChannelById(channelId).flatMap {
            // TODO write result to db
            val message = it.restChannel.getRestMessage(messageId)
            message.deleteAllReactions()
        }.subscribe()
    }
}
