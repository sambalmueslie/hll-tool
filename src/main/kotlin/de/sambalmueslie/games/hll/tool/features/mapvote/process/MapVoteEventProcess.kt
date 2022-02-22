package de.sambalmueslie.games.hll.tool.features.mapvote.process


import de.sambalmueslie.games.hll.tool.features.mapvote.MapVoteService
import de.sambalmueslie.games.hll.tool.features.mapvote.discord.MapVoteBotEventHandler
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.event.domain.message.ReactionRemoveEvent
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Singleton
class MapVoteEventProcess(
    private val service: MapVoteService
) : MapVoteBotEventHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteEventProcess::class.java)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun handleReactionEvent(event: ReactionAddEvent): Mono<Void> {
        val messageId = event.messageId.asLong()
        val voting = service.getByMessageId(messageId) ?: return Mono.empty()
        val user = event.user.block() ?: return Mono.empty()
        voting.addVote(user, event.emoji)

        val message = event.message.block() ?: return Mono.empty()

        // TODO filter out duplicates

        return Mono.empty()
    }

    override fun handleReactionEvent(event: ReactionRemoveEvent): Mono<Void> {
        val messageId = event.messageId.asLong()
        val voting = service.getByMessageId(messageId) ?: return Mono.empty()
        val user = event.user.block() ?: return Mono.empty()
        voting.removeVote(user, event.emoji)

        return Mono.empty()
    }
}
