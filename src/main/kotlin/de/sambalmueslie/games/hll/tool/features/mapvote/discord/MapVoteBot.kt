package de.sambalmueslie.games.hll.tool.features.mapvote.discord


import de.sambalmueslie.games.hll.tool.config.MapVoteConfig
import discord4j.common.util.Snowflake
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import discord4j.core.event.domain.message.*
import discord4j.core.spec.MessageCreateSpec
import io.micronaut.context.annotation.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Context
class MapVoteBot(
    config: MapVoteConfig,
    private val connector: DiscordConnector,
    private val handler: Set<MapVoteBotEventHandler>
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteBot::class.java)
        const val CMD_PREFIX = "!mapvote"
    }


    init {
        val eventDispatcher = connector.getEventDispatcher()
        eventDispatcher.on(MessageCreateEvent::class.java)
            .flatMap { evt -> handleMessageEvent("create", evt) { it.handleMessageEvent(evt) } }.subscribe()
        eventDispatcher.on(MessageUpdateEvent::class.java)
            .flatMap { evt -> handleMessageEvent("update", evt) { it.handleMessageEvent(evt) } }.subscribe()
        eventDispatcher.on(MessageDeleteEvent::class.java)
            .flatMap { evt -> handleMessageEvent("delete", evt) { it.handleMessageEvent(evt) } }.subscribe()
        eventDispatcher.on(MessageBulkDeleteEvent::class.java)
            .flatMap { evt -> handleMessageEvent("bulk delete", evt) { it.handleMessageEvent(evt) } }.subscribe()

        eventDispatcher.on(ReactionAddEvent::class.java)
            .flatMap { evt -> handleReactionEvent("add", evt) { it.handleReactionEvent(evt) } }.subscribe()
        eventDispatcher.on(ReactionRemoveEvent::class.java)
            .flatMap { evt -> handleReactionEvent("remove", evt) { it.handleReactionEvent(evt) } }.subscribe()
        eventDispatcher.on(ReactionRemoveAllEvent::class.java)
            .flatMap { evt -> handleReactionEvent("remove all", evt) { it.handleReactionEvent(evt) } }.subscribe()
        eventDispatcher.on(ReactionRemoveEmojiEvent::class.java)
            .flatMap { evt -> handleReactionEvent("remove emoji", evt) { it.handleReactionEvent(evt) } }.subscribe()
    }

    private fun <T : MessageEvent> handleMessageEvent(type: String, event: T, action: (MapVoteBotEventHandler) -> Mono<Void>): Mono<Void> {
        logger.debug("Handle message $type event $event")
        return Flux.fromIterable(handler).flatMap { action.invoke(it) }.next()
    }

    private fun <T : MessageEvent> handleReactionEvent(type: String, event: T, action: (MapVoteBotEventHandler) -> Mono<Void>): Mono<Void> {
        logger.debug("Handle reaction $type event $event")
        return Flux.fromIterable(handler).flatMap { action.invoke(it) }.next()
    }

}
