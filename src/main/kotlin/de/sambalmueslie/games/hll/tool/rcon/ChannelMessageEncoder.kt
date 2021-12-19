package de.sambalmueslie.games.hll.tool.rcon

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import kotlin.experimental.xor

class ChannelMessageEncoder(private val client: RconClient) : MessageToMessageEncoder<String>() {

    override fun encode(ctx: ChannelHandlerContext, msg: String, out: MutableList<Any>) {
        if (msg.isEmpty()) return

        out.add(xor(msg))
    }

    private fun xor(command: String): ByteBuf {
        val raw = command.toByteArray(Charsets.UTF_8)
        val buf = Unpooled.buffer(raw.size)
        val key = client.key
        raw.mapIndexed { index, byte -> byte.xor(key[index % key.size]) }.forEach { buf.writeByte(it.toInt()) }
        return buf
    }

}
