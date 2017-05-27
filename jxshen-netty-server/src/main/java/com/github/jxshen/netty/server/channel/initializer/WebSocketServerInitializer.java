package com.github.jxshen.netty.server.channel.initializer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;

/**
 * reference from: netty in action
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    
    private final SSLContext context;
    private final boolean client;

    public WebSocketServerInitializer(SSLContext context, boolean client) {
        super();
        this.context = context;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        ChannelPipeline pipe = ch.pipeline();
        pipe.addFirst("ssl", new SslHandler(engine));
        if (client) {
            pipe.addLast("codec", new HttpClientCodec());
        }
        else {
            pipe.addLast("codec", new HttpServerCodec());
        }
        pipe.addLast("decompressor", new HttpContentDecompressor());
        pipe.addLast("aggregator", new HttpObjectAggregator(65535));
        pipe.addLast("webSocket", new WebSocketServerProtocolHandler("/websocket"));
        pipe.addLast(new TextFrameHandler());
        pipe.addLast(new BinaryFrameHandler());
        pipe.addLast(new ContinuationFrameHandler());
    }
    
    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
                throws Exception {
        }
    }
    
    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg)
                throws Exception {
        }
    }
    
    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg)
                throws Exception {
        }
    }

}
