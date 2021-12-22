package de.sambalmueslie.games.hll.tool.features.mapvote


import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import de.sambalmueslie.games.hll.tool.config.MapVoteConfig
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteServerSettingsRepository
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import discord4j.common.util.Snowflake
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.MessageCreateSpec
import discord4j.rest.util.Color
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant


@Singleton
class MapVoteService(
    private val config: MapVoteConfig,
    private val mapRepository: MapRepository,
    private val repository: MapVoteServerSettingsRepository
) : MapChangeListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteService::class.java)
        private const val CMD_PREFIX = "!mapvote"
        private const val MAX_MAP_VOTES = 8
    }

    private val commands = mutableMapOf<String, MapVoteCommand>()
    private val client: GatewayDiscordClient = DiscordClientBuilder.create(config.token).build().login().block()!!

    init {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .flatMap { evt ->
                logger.info("Got a message [${evt.message.content}] from ${evt.message.author}}")
                Mono.justOrEmpty(evt.message.content).flatMap { content ->
                    Flux.fromIterable(commands.entries)
                        .filter { content.startsWith("$CMD_PREFIX ${it.key}") }
                        .flatMap { it.value.execute(evt) }
                        .next()
                }
            }
            .subscribe()

        client.eventDispatcher.on(ReactionAddEvent::class.java)
            .flatMap { evt ->
                logger.info("Got reaction ${evt.emoji}")
                Mono.empty<Void>()
            }.subscribe()


        commands["ping"] = object : MapVoteCommand {
            override fun execute(event: MessageCreateEvent): Mono<Void> {
                return event.message.channel.flatMap {
                    logger.debug("Create pong message on channel ${it.id}")
                    createPongMessage(event, it)
                }.then()
            }
        }

        commands["help"] = object : MapVoteCommand {
            override fun execute(event: MessageCreateEvent): Mono<Void> {
                return event.message.channel.flatMap { createHelpMessage(event, it) }.then()
            }
        }

        commands["register"] = object : MapVoteCommand {
            override fun execute(event: MessageCreateEvent): Mono<Void> {
                val author = event.message.author.orElseGet { null } ?: return Mono.empty()
                registrationProcess.put(author.id.asLong(), RegistrationProcess(event))
                return author.privateChannel.flatMap { createStartRegistrationMessage(event, it) }.then()
            }
        }
    }

    private val registrationProcess: Cache<Long, RegistrationProcess> = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(5))
        .build()

    private fun handlePrivateResponse(event: ChatInputInteractionEvent): Mono<Void> {
        // TODO not implemented yet
        return Mono.empty()
    }

    override fun handleMapChanged(instance: ServerInstance, data: MapData) {
        val settings = repository.findByServerId(instance.id) ?: return
        if (!settings.enabled) return

        val availableMaps = instance.mapsInRotation
        val lastMaps = mapRepository.findFirst5ByServerIdOrderByTimestampDesc(instance.id).map { it.name }

        val mapsForVote = (availableMaps - lastMaps.toSet()).shuffled().take(MAX_MAP_VOTES).toSet()

        logger.info("Maps for vote: $mapsForVote")
        if (mapsForVote.isEmpty()) return

        client.getChannelById(Snowflake.of(settings.userChannelId))
            .ofType(GuildMessageChannel::class.java)
            .flatMap {
                val mapSelectionContent = StringBuilder("")
                mapsForVote.forEachIndexed { index, map -> mapSelectionContent.append("${numberEmoji[index+1]} $map \n\n") }
                val msgSpec = MessageCreateSpec.builder().content("Active map vote for next map")
                msgSpec.addEmbed(
                    EmbedCreateSpec.builder()
                        .color(Color.GREEN)
                        .description(mapSelectionContent.toString())
                        .timestamp(Instant.now())
                        .build()
                )
                it.createMessage(msgSpec.build())
            }
            .flatMap { msg ->
                List(mapsForVote.size) { index -> msg.addReaction(mapVoteEmojis[index+1]) }
                    .reduce { last, current -> last.then(current) }
            }
            .subscribe()
    }

}
