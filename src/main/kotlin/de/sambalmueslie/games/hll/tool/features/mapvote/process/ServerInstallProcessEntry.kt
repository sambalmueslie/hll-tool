//package de.sambalmueslie.games.hll.tool.features.mapvote.process
//
//
//import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteServerSettingsData
//import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteServerSettingsRepository
//import de.sambalmueslie.games.hll.tool.monitor.server.ServerService
//import de.sambalmueslie.games.hll.tool.monitor.server.api.ServerChangeRequest
//import de.sambalmueslie.games.hll.tool.monitor.server.api.ServerSettingsChangeRequest
//import discord4j.common.util.Snowflake
//import discord4j.core.`object`.entity.Guild
//import discord4j.core.`object`.entity.channel.TextChannel
//import discord4j.core.event.domain.message.MessageCreateEvent
//import discord4j.core.spec.EmbedCreateSpec
//import discord4j.core.spec.MessageCreateSpec
//import discord4j.rest.util.Color
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import reactor.core.publisher.Mono
//import java.time.Instant
//
//
//internal class ServerInstallProcessEntry(
//    private val repository: MapVoteServerSettingsRepository,
//    private val serverService: ServerService,
//    private var state: ServerInstallState = ServerInstallState.INIT,
//) {
//
//    companion object {
//        val logger: Logger = LoggerFactory.getLogger(ServerInstallProcessEntry::class.java)
//        private val ADD_SERVER_REGEX = "(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}) (\\d+) (\\S+)".toRegex()
//    }
//
//    fun handleMessageEvent(event: MessageCreateEvent, content: String, guildId: Long): Mono<Void> {
//        return when (state) {
//            ServerInstallState.INIT -> event.guild.flatMap { createDiscordChannels(event, it) }
//            ServerInstallState.ADD_SERVER -> registerServer(event, content)
//            else -> Mono.empty()
//        }
//    }
//
//    @Suppress("BlockingMethodInNonBlockingContext")
//    private fun registerServer(event: MessageCreateEvent, content: String): Mono<Void> {
//        val channelId = event.message.channelId.asLong()
//        val data = repository.findByAdminChannelId(channelId) ?: return Mono.empty()
//
//        val matcher = ADD_SERVER_REGEX.matchEntire(content.trim()) ?: return event.message.channel.flatMap { it.createMessage(invalidServerInputMessage(content)).then() }
//
//        val host = matcher.groupValues[1]
//        val port = matcher.groupValues[2].toInt()
//        val password = matcher.groupValues[3]
//
//        val request = ServerChangeRequest("", host, port, password, ServerSettingsChangeRequest(mapTrackingEnabled = true, slotTrackingEnabled = false, logTrackingEnabled = true))
//        val instance = serverService.create(request) ?: return event.message.channel.flatMap { it.createMessage(failedToCreateServerInstance(content)).then() }
//
//        data.serverId = instance.id
//        data.enabled = true
//        repository.update(data)
//
//        state = ServerInstallState.DONE
//        return event.message.delete().then(event.message.channel.flatMap { it.createMessage(installationFinishedSuccessfully()).then() })
//    }
//
//
//    @Suppress("BlockingMethodInNonBlockingContext")
//    private fun createDiscordChannels(event: MessageCreateEvent, guild: Guild): Mono<Void> {
//        if (!verifyServerOwner(event, guild)) return Mono.empty()
//
//        state = ServerInstallState.SETUP_DISCORD
//
//        val guildId = guild.id.asLong()
//        var data = repository.findByGuildId(guildId) ?: repository.save(MapVoteServerSettingsData(guildId = guildId))
//
//        val adminChannel = if (data.adminChannelId < 0) {
//            // TODO check if channel already existing
//            val channel = guild.createTextChannel("map-vote-admin").block() ?: return Mono.empty()
//            channel.createMessage(adminChannelCreatedMessage()).block()
//            data.adminChannelId = channel.id.asLong()
//            data = repository.update(data)
//            channel
//        } else {
//            guild.getChannelById(Snowflake.of(data.adminChannelId)).ofType(TextChannel::class.java).block() ?: return Mono.empty()
//        }
//
//        val voteChannel = if (data.userChannelId < 0) {
//            // TODO check if channel already existing
//            val channel = guild.createTextChannel("map-vote").block() ?: return Mono.empty()
//            channel.createMessage(voteChannelCreatedMessage()).block()
//            data.userChannelId = channel.id.asLong()
//            data = repository.update(data)
//            channel
//        } else {
//            guild.getChannelById(Snowflake.of(data.userChannelId)).ofType(TextChannel::class.java).block() ?: return Mono.empty()
//        }
//
//        state = ServerInstallState.ADD_SERVER
//        return adminChannel.createMessage(enterServerSettingsMessage()).then()
//    }
//
//
//    private fun verifyServerOwner(event: MessageCreateEvent, guild: Guild): Boolean {
//        val author = event.message.author.orElseGet { null } ?: return false
//        return guild.ownerId == author.id
//    }
//
//
//    private fun adminChannelCreatedMessage(): MessageCreateSpec {
//        return MessageCreateSpec.builder().content("Admin channel created successfully.").build()
//    }
//
//    private fun voteChannelCreatedMessage(): MessageCreateSpec {
//        return MessageCreateSpec.builder().content("Vote channel created successfully.").build()
//    }
//
//
//    private fun enterServerSettingsMessage(): MessageCreateSpec {
//        val spec = MessageCreateSpec.builder()
//        spec.addEmbed(
//            EmbedCreateSpec.builder()
//                .color(Color.GREEN)
//                .title("Setup process")
//                .description("This will help you setup the map vote process. Reply with the server settings (IP Port RCON-PW)")
//                .timestamp(Instant.now())
//                .build()
//        )
//        return spec.build()
//    }
//
//    private fun invalidServerInputMessage(content: String): MessageCreateSpec {
//        return MessageCreateSpec.builder().content("Invalid input '$content' for setup server").build()
//    }
//
//    private fun failedToCreateServerInstance(content: String): MessageCreateSpec {
//        return MessageCreateSpec.builder().content("Cannot create server instance for '$content'").build()
//    }
//
//    private fun installationFinishedSuccessfully(): MessageCreateSpec {
//        return MessageCreateSpec.builder().content("Installation finished successfully :thumbsup:").build()
//    }
//}
