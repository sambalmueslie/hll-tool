package de.sambalmueslie.games.hll.rcon


import de.sambalmueslie.games.hll.tool.rcon.RconClient
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ChannelResponseHandler(private val client: RconClient) : SimpleChannelInboundHandler<String>() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ChannelResponseHandler::class.java)
    }

    private val buffer = StringBuilder()
    private var received: Boolean = false

    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        buffer.append(msg)
        received = true
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        if (received) {
            client.handleResponse(buffer.toString())
            buffer.clear()
            received = false
        }
        super.channelReadComplete(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        client.handleError(cause)
    }

}
