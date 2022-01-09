package de.sambalmueslie.games.hll.tool.features.mapvote


import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessData
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessEntryData
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessEntryRepository
import de.sambalmueslie.games.hll.tool.features.mapvote.db.MapVoteProcessRepository
import de.sambalmueslie.games.hll.tool.features.mapvote.process.MapVoteFinishProcess
import de.sambalmueslie.games.hll.tool.game.api.MapInfo
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import discord4j.core.`object`.entity.Message
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration


@Singleton
class MapVoteService(
    private val finishProcess: MapVoteFinishProcess,
    private val processRepository: MapVoteProcessRepository,
    private val processEntryRepository: MapVoteProcessEntryRepository,
) {


    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteService::class.java)
        private const val CACHE_SIZE = 1000L
        private val EXPIRE_DURATION = Duration.ofMinutes(95)
    }

    init {

        // TODO sync with database
    }


    private val messageVotingRelationCache: Cache<Long, ServerMapVoting> = Caffeine.newBuilder()
        .maximumSize(CACHE_SIZE)
        .expireAfterWrite(EXPIRE_DURATION)
        .build()

    private val cache: Cache<Long, ServerMapVoting> = Caffeine.newBuilder()
        .maximumSize(CACHE_SIZE)
        .expireAfterWrite(EXPIRE_DURATION)
        .build()

    fun create(message: Message, instance: ServerInstance, activeMap: MapData, activeMapInfo: MapInfo, mapsForVote: List<MapInfo>) {
        val existing = cache.getIfPresent(instance.id)
        if (existing != null) {
            finish(existing)
            cache.invalidate(instance.id)
            messageVotingRelationCache.invalidate(existing.message.id.asLong())
            // TODO delete process
        }


        val discordMessageId = message.id.asLong()
        val data = processRepository.save(MapVoteProcessData(0, instance.id, activeMap.id, discordMessageId))
        val entries = processEntryRepository.saveAll(mapsForVote.map { MapVoteProcessEntryData(0, data.id, it.id) }).toList()

        val voting = ServerMapVoting(message, instance, activeMap, activeMapInfo, mapsForVote, data, entries)
        cache.put(instance.id, voting)
        messageVotingRelationCache.put(discordMessageId, voting)
    }

    private fun finish(existing: ServerMapVoting) {
        finishProcess.handleFinished(existing)
    }

    fun getByMessageId(messageId: Long): ServerMapVoting? {
        return messageVotingRelationCache.getIfPresent(messageId)
    }

}
