package de.sambalmueslie.games.hll.tool.rcon


import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ChannelLoginHandler(private val client: RconClient) : SimpleChannelInboundHandler<String>() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ChannelLoginHandler::class.java)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        val loggedIn = client.isLoggedIn()
        if (loggedIn) {
            ctx.fireChannelRead(msg)
        } else if (msg.isEmpty()) {
            val command = "login ${client.config.password}"
            ctx.writeAndFlush(command).sync()
        } else if (msg == "SUCCESS") {
            client.handleLogin(true)
        } else {
            logger.error("Uncovered response: '$msg'")
            client.handleLogin(false)
        }
    }

}
