package de.sambalmueslie.games.hll.tool.features.mapvote

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.MessageCreateMono
import discord4j.core.spec.MessageCreateSpec
import discord4j.rest.util.Color
import reactor.core.publisher.Mono
import java.time.Instant

fun createPongMessage(event: MessageCreateEvent, channel: MessageChannel): MessageCreateMono {
    return channel.createMessage("Pong ${event.message.author.orElse(null)?.username ?: "stranger"}")
}

fun createHelpMessage(event: MessageCreateEvent, channel: MessageChannel): Mono<Message> {
    val spec = MessageCreateSpec.builder()
    spec.addEmbed(
        EmbedCreateSpec.builder()
            .color(Color.GREEN)
            .author("How to use map vote bot", "", "")
            .title("Map Vote Bot Help")
            .description("Map Vote Bot helps you with your HLL Map voting")
            .addField("!mapvote help", "Show help info", false)
            .addField("!mapvote ping", "Reply with pong", false)
            .addField("!mapvote register", "Setup the registration process", false)
            .timestamp(Instant.now())
            .build()
    )
    return channel.createMessage(spec.build())
}

fun createStartRegistrationMessage(event: MessageCreateEvent, channel: MessageChannel): Mono<Message> {
    val spec = MessageCreateSpec.builder()
    spec.addEmbed(
        EmbedCreateSpec.builder()
            .color(Color.GREEN)
            .title("Setup process")
            .description("This will help you setup the map vote process. Reply with the server settings (IP Port RCON-PW)")
            .timestamp(Instant.now())
            .build()
    )
    return channel.createMessage(spec.build())
}

val numberEmoji = listOf(":zero:", ":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:")
val numberReactionEmojis = listOf(
    ReactionEmoji.unicode("0️⃣"),
    ReactionEmoji.unicode("1️⃣"),
    ReactionEmoji.unicode("2️⃣"),
    ReactionEmoji.unicode("3️⃣"),
    ReactionEmoji.unicode("4️⃣"),
    ReactionEmoji.unicode("5️⃣"),
    ReactionEmoji.unicode("6️⃣"),
    ReactionEmoji.unicode("7️⃣"),
    ReactionEmoji.unicode("8️⃣"),
    ReactionEmoji.unicode("9️⃣"),
    ReactionEmoji.unicode("\uD83D\uDD1F"),
)
