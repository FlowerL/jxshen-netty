package com.github.jxshen.netty.server.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * reference from: http://www.cnblogs.com/zou90512/p/3492878.html
 */
public class NettyHelloServer {
    
    // listener port
    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new HelloServerInitializer());
        
        try {
            ChannelFuture f = b.bind(PORT).sync();
            // listen server to shutdown listener
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        
    }
}
