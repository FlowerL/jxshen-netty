package com.github.jxshen.netty.server.channel.initializer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

/**
 * reference from: netty in action
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;
    private final boolean client;
    
    public HttpAggregatorInitializer(SSLContext context, boolean client) {
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
            pipe.addLast("decompressor", new HttpContentDecompressor());
        } else {
            pipe.addLast("codec", new HttpServerCodec());
            pipe.addLast("decompressor", new HttpContentDecompressor());
        }
        pipe.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
    }

}
