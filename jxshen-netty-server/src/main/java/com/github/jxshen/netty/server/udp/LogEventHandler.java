package com.github.jxshen.netty.server.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * reference from: netty in action<br>
 * add function: when meet space char, do not print
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (msg.getMsg() == null || msg.getMsg().length() == 0) {
            return;
        }
        builder.append(msg.getReceived());
        builder.append("[");
        builder.append(msg.getSource().toString());
        builder.append("][");
        builder.append(msg.getLogFile());
        builder.append("] : ");
        builder.append(msg.getMsg());
        System.out.println(builder.toString());
    }

}
