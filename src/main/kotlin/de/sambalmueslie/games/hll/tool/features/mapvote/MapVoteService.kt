package de.sambalmueslie.games.hll.tool.features.mapvote


import de.sambalmueslie.games.hll.tool.config.MapVoteConfig
import de.sambalmueslie.games.hll.tool.monitor.map.api.MapChangeListener
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapData
import de.sambalmueslie.games.hll.tool.monitor.map.db.MapRepository
import de.sambalmueslie.games.hll.tool.monitor.server.ServerInstance
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Singleton
class MapVoteService(
    private val config: MapVoteConfig,
    private val mapRepository: MapRepository
) : MapChangeListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteService::class.java)
    }

    //    private val client = DiscordClient.create(config.token)
//
    override fun handleMapChanged(instance: ServerInstance, data: MapData) {
//        val availableMaps = instance.mapsInRotation
//        val lastMaps = mapRepository.findFirst5ByServerIdOrderByTimestampDesc(instance.id).map { it.name }
//
//        val mapsForVote = availableMaps - lastMaps
//
//        val login = client.withGateway { gateway: GatewayDiscordClient ->
//            gateway.on(
//                ReadyEvent::class.java
//            ) { event: ReadyEvent ->
//                Mono.fromRunnable<Any?> {
//                    val self = event.self
//                    logger.info("Logged in as %s#%s%n", self.username, self.discriminator)
//                }
//            }
//        }
//        login.block()
    }

}
