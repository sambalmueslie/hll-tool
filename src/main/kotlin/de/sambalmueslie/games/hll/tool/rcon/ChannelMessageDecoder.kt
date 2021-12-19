package de.sambalmueslie.games.hll.tool.rcon


import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.experimental.xor

class ChannelMessageDecoder(private val client: RconClient) : MessageToMessageDecoder<ByteBuf>() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ChannelMessageDecoder::class.java)
    }


    override fun decode(ctx: ChannelHandlerContext?, msg: ByteBuf, out: MutableList<Any>) {
        if (client.isConnected.get()) {
            out.add(parseResponse(msg))
        }
    }

    private fun parseResponse(msg: ByteBuf): String {
        val data = ByteArray(msg.readableBytes())
        msg.readBytes(data)
        val key = client.key
        return data.mapIndexed { index, byte -> byte.xor(key[index % key.size]) }
            .toByteArray()
            .toString(Charsets.UTF_8)
    }

}
