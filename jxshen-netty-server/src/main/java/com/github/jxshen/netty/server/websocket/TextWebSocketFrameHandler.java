package com.github.jxshen.netty.server.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * reference from: netty in action
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        super();
        this.group = group;
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // if handshake of websocket complete
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // delete channelPipeline's HttpRequestHandler
            ctx.pipeline().remove(HttpRequestHandler.class);
            // write a msg to channelGroup
            group.writeAndFlush(new TextWebSocketFrame("client " + ctx.channel() + " joined"));
            // add channel to channelGroup
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        // transfer received msg to all connected clients by channelGroup
        group.writeAndFlush(msg.retain());
    }

}
