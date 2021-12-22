package de.sambalmueslie.games.hll.tool.features.mapvote

import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

interface MapVoteCommand {
    fun execute(event: MessageCreateEvent): Mono<Void>
}
