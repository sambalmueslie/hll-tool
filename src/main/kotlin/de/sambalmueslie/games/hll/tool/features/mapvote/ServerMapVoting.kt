package de.sambalmueslie.games.hll.tool.features.mapvote


import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessData
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessEntryData
import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.reaction.ReactionEmoji
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ServerMapVoting(
    val message: Message,
    val instance: ServerInstance,
    val activeMap: MapData,
    val activeMapInfo: MapInfo,
    val mapsForVote: List<MapInfo>,
    private val data: MapVoteProcessData,
    private val entries: List<MapVoteProcessEntryData>
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ServerMapVoting::class.java)
    }

    fun addVote(user: User, emoji: ReactionEmoji) {
        val map = getMapForEmoji(emoji) ?: return logger.error("Cannot count voting, cause emoji $emoji was not found")
        logger.info("Add voting for map ${map.text} by ${user.username}")
        // TODO("Not yet implemented")
    }

    fun removeVote(user: User, emoji: ReactionEmoji) {
        val map = getMapForEmoji(emoji) ?: return logger.error("Cannot count voting, cause emoji $emoji was not found")
        logger.info("Remove voting for map ${map.text} by ${user.username}")
        // TODO("Not yet implemented")
    }

    private fun getMapForEmoji(emoji: ReactionEmoji): MapInfo? {
        val index = numberReactionEmojis.indexOf(emoji)
        if (index < 0 || index >= mapsForVote.size) return null

        return mapsForVote[index]
    }

}
