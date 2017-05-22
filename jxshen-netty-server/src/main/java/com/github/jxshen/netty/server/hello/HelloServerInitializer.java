package com.github.jxshen.netty.server.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * reference from: http://www.cnblogs.com/zou90512/p/3492878.html
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        // Decoder of read, delimiter "\n" as end
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        
        // Chars decoder
        pipeline.addLast("decoder", new StringDecoder());
        // Chars encoder
        pipeline.addLast("encoder", new StringEncoder());
        
        // user logic
        pipeline.addLast("handler", new HelloServerHandler());
        
    }

}
