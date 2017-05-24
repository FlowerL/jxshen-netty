package com.github.jxshen.netty.server.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * reference from: netty in action
 */
public class HttpDecoderEncoderInitializer extends ChannelInitializer<Channel> {

    private final boolean client;
    
    public HttpDecoderEncoderInitializer(boolean client) {
        super();
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipe = ch.pipeline();
        if (client) {
            pipe.addLast("decoder", new HttpResponseDecoder());
            pipe.addLast("", new HttpRequestEncoder());
        } else {
            pipe.addLast("decoder", new HttpRequestDecoder());
            pipe.addLast("encoder", new HttpResponseEncoder());
        }
    }

}
