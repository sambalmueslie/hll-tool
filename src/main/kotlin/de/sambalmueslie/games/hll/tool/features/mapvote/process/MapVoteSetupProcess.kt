//package de.sambalmueslie.games.hll.tool.features.mapvote.process
//
//
//import de.sambalmueslie.games.hll.tool.features.mapvote.MapVoteService
//import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteServerSettingsRepository
//import de.sambalmueslie.games.hll.tool.features.mapvote.discord.DiscordConnector
//import de.sambalmueslie.games.hll.tool.features.mapvote.numberEmoji
//import de.sambalmueslie.games.hll.tool.features.mapvote.numberReactionEmojis
//import de.sambalmueslie.games.hll.tool.game.MapService
//import de.sambalmueslie.games.hll.tool.game.api.MapInfo
//import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
//import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
//import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
//import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
//import discord4j.common.util.Snowflake
//import discord4j.core.`object`.entity.Message
//import discord4j.core.`object`.entity.channel.GuildMessageChannel
//import discord4j.core.spec.EmbedCreateSpec
//import discord4j.core.spec.MessageCreateSpec
//import discord4j.rest.util.Color
//import jakarta.inject.Singleton
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import reactor.core.publisher.Mono
//import java.time.Instant
//
//@Singleton
//class MapVoteSetupProcess(
//    private val connector: DiscordConnector,
//    private val mapRepository: MapRepository,
//    private val mapService: MapService,
//    private val repository: MapVoteServerSettingsRepository,
//    private val service: MapVoteService
//) : MapChangeListener {
//
//    companion object {
//        val logger: Logger = LoggerFactory.getLogger(MapVoteSetupProcess::class.java)
//    }
//
//    override fun handleMapChanged(instance: ServerInstance, data: MapData) {
//        val settings = repository.findByServerId(instance.id) ?: return
//        if (!settings.enabled) return
//
//        val availableMaps = instance.mapsInRotation
//        val lastMaps = mapRepository.findFirst5ByServerIdOrderByTimestampDesc(instance.id).map { it.name }
//
//        val mapsForVote = (availableMaps - lastMaps.toSet()).shuffled().take(settings.mapVoteAmount)
//            .mapNotNull { mapService.getInfo(it, "en") }
//
//        logger.info("Maps for vote: $mapsForVote")
//        if (mapsForVote.isEmpty()) return
//
//        val info = mapService.getInfo(data.name, "en")!!
//
//        connector.getChannelById(Snowflake.of(settings.userChannelId))
//            .ofType(GuildMessageChannel::class.java)
//            .flatMap { it.createMessage(createMapVoteMessage(mapsForVote, info)) }
//            .flatMap { createProcess(it, instance, data, info, mapsForVote) }
//            .flatMap { addMapVoteEmojis(it, mapsForVote) }
//            .subscribe()
//    }
//
//    private fun createProcess(message: Message, instance: ServerInstance, activeMap: MapData, activeMapInfo: MapInfo, mapsForVote: List<MapInfo>): Mono<Message> {
//        service.create(message, instance, activeMap, activeMapInfo, mapsForVote)
//        return Mono.justOrEmpty(message)
//    }
//
//    private fun addMapVoteEmojis(msg: Message, mapsForVote: List<MapInfo>): Mono<Void> {
//        return List(mapsForVote.size) { index -> msg.addReaction(numberReactionEmojis[index + 1]) }
//            .reduce { last, current -> last.then(current) }
//    }
//
//    private fun createMapVoteMessage(mapsForVote: List<MapInfo>, activeMap: MapInfo): MessageCreateSpec {
//        val mapSelectionContent = StringBuilder("")
//        mapsForVote.forEachIndexed { index, info ->
//            val emoji = numberEmoji[index + 1]
//            mapSelectionContent.append("$emoji **${info.text}** \t ${info.type} \t [${info.attacker.text} vs. ${info.defender.text}] \n\n")
//        }
//        val spec = MessageCreateSpec.builder()
//            .content("Active map vote for next map")
//        val embed = EmbedCreateSpec.builder()
//            .color(Color.GRAY)
//            .description(mapSelectionContent.toString())
//            .timestamp(Instant.now())
//            .title("**${activeMap.text}** \t ${activeMap.type} \t [${activeMap.attacker.text} vs. ${activeMap.defender.text}]")
//        spec.addEmbed(embed.build())
//        return spec.build()
//    }
//
//
//}
