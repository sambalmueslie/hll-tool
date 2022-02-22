package de.sambalmueslie.games.hll.tool.features.mapvote.process


import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteServerSettingsRepository
import de.sambalmueslie.games.hll.tool.features.mapvote.discord.MapVoteBot
import de.sambalmueslie.games.hll.tool.features.mapvote.discord.MapVoteBotEventHandler
import de.sambalmueslie.games.hll.tool.monitor.server.ServerService
import discord4j.core.event.domain.message.MessageCreateEvent
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.Duration

@Singleton
class ServerInstallProcess(
    private val repository: MapVoteServerSettingsRepository,
    private val serverService: ServerService,
) : MapVoteBotEventHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerInstallProcess::class.java)
    }

    private val activeEntries: Cache<Long, ServerInstallProcessEntry> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(5))
        .build()

    override fun handleMessageEvent(event: MessageCreateEvent): Mono<Void> {
        val author = event.message.author.orElseGet { null } ?: return Mono.empty()
        if (author.isBot) return Mono.empty()

        val content = event.message.content

        val guildId = event.guildId.orElseGet { null }?.asLong() ?: return Mono.empty()
        val entry = activeEntries.getIfPresent(guildId)

        return if (entry == null) {
            if (!content.startsWith("${MapVoteBot.CMD_PREFIX} install")) return Mono.empty()
            val newEntry = ServerInstallProcessEntry(repository, serverService)
            activeEntries.put(guildId, newEntry)
            newEntry.handleMessageEvent(event, content, guildId)
        } else {
            entry.handleMessageEvent(event, content, guildId)
        }

    }


}
