package com.github.jxshen.netty.server.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;
    
    public ChatServerInitializer(ChannelGroup group) {
        super();
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipe = ch.pipeline();
        // encode & decode http request
        pipe.addLast(new HttpServerCodec());
        // write file content
        pipe.addLast(new ChunkedWriteHandler());
        // aggregate encode & decode of HttpRequest/HttpContent/HttpLastContent to FullHttpRequest
        // to ensure the completion of http request
        pipe.addLast(new HttpObjectAggregator(64 * 1024));
        // handle FullHttpRequest
        pipe.addLast(new HttpRequestHandler("/ws"));
        // handle other webSocketFrame
        pipe.addLast(new WebSocketServerProtocolHandler("/ws"));
        // handle TextWebSocketFrame
        pipe.addLast(new TextWebSocketFrameHandler(group));
    }

}
