package de.sambalmueslie.games.hll.tool.rcon.api

import de.sambalmueslie.games.hll.tool.rcon.RconClientConfig

interface HellLetLooseClientFactory {
    fun create(config: RconClientConfig): HellLetLooseClient
}
