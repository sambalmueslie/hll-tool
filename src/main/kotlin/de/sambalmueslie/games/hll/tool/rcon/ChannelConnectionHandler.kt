package de.sambalmueslie.games.hll.tool.rcon


import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ChannelConnectionHandler(private val client: RconClient) : ChannelInboundHandlerAdapter() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ChannelConnectionHandler::class.java)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val m = msg as ByteBuf
        if (!client.isConnected.get() && m.readableBytes() == 4) {
            m.readBytes(client.key)
            client.isConnected.set(true)
        }
        super.channelRead(ctx, msg)
    }

}
