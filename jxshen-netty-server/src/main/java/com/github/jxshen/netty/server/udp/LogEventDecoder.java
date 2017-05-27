package com.github.jxshen.netty.server.udp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

/**
 * reference from: netty in action<br>
 * </br>
 * <b>fix bug</b>: in book the logMsg slice start at i+1 and length is buf.readableBytes()<br>
 * but slice do not influence the readerIndex & writerIndex<br>
 * so in this code the buf.readableBytes will not change<br>
 * which leads logMsg.length() to be error<br>
 * So I fix the slice length to buf.readableBytes() - i - 1<br>
 * notice the LogEvent.SEPARATOR also take up one position so we need subtract 1 more
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out)
            throws Exception {
        ByteBuf buf = msg.content();
        int i = buf.indexOf(0, buf.readableBytes(), LogEvent.SEPARATOR);
        String filename = buf.slice(0, i).toString(CharsetUtil.UTF_8);
        String logMsg = buf.slice(i+1, buf.readableBytes() - i - 1).toString(CharsetUtil.UTF_8);
        logMsg.replaceAll("/r|/n", "");
        LogEvent event = new LogEvent(msg.sender(), filename, logMsg, System.currentTimeMillis());
        out.add(event);
    }

}
