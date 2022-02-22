package de.sambalmueslie.games.hll.tool.features.mapvote.discord


import de.sambalmueslie.games.hll.tool.config.MapVoteConfig
import discord4j.common.util.Snowflake
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class DiscordConnector(config: MapVoteConfig) {


    companion object {
        val logger: Logger = LoggerFactory.getLogger(DiscordConnector::class.java)
    }


    private val client: GatewayDiscordClient = DiscordClientBuilder.create(config.token).build().login().block()!!

    fun getChannelById(channelId: Snowflake) = client.getChannelById(channelId)
    fun getEventDispatcher() = client.eventDispatcher
}
