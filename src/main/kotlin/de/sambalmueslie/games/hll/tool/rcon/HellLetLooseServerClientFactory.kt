package de.sambalmueslie.games.hll.tool.rcon


import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClient
import de.sambalmueslie.games.hll.tool.rcon.api.HellLetLooseClientFactory
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class HellLetLooseServerClientFactory : HellLetLooseClientFactory {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HellLetLooseServerClientFactory::class.java)
    }

    override fun create(config: RconClientConfig): HellLetLooseClient {
        logger.info("Create client ${config.host}:${config.port}")
        return HellLetLooseServerClient(config)
    }


}
