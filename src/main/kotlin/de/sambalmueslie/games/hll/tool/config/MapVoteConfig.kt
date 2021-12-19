package de.sambalmueslie.games.hll.tool.config


import io.micronaut.context.annotation.ConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.constraints.NotBlank

@ConfigurationProperties("app.map.vote")
class MapVoteConfig {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MapVoteConfig::class.java)
    }

    @NotBlank
    var token: String = ""
        set(value) {
            logger.info("Set token to ${value.replace(".".toRegex(), "#")}")
            field = value
        }


}
