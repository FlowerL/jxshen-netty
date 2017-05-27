package com.github.jxshen.netty.server.udp;

import java.net.InetSocketAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

/**
 * reference from: netty in action
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;
    
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        super();
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out)
            throws Exception {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(msg.getLogFile().getBytes(CharsetUtil.UTF_8));
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msg.getMsg().getBytes(CharsetUtil.UTF_8));
        out.add(new DatagramPacket(buf, remoteAddress));
    }

}
