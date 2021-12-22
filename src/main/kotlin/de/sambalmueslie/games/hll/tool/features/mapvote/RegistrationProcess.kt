package de.sambalmueslie.games.hll.tool.features.mapvote


import discord4j.core.event.domain.message.MessageCreateEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RegistrationProcess(
    private val event: MessageCreateEvent
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(RegistrationProcess::class.java)
    }


}
