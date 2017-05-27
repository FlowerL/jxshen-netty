package com.github.jxshen.netty.server.bootstrap;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * reference from: netty in action
 * 
 * bootstrap client from channel
 */
public class BootstrapingFromChannel {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
        .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
            ChannelFuture connectFuture;
            
            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                Bootstrap b = new Bootstrap();
                b.channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
                            throws Exception {
                        System.out.println("Receive data");
                        msg.clear();
                    }
                });
                b.group(ctx.channel().eventLoop());
                connectFuture = b.connect(new InetSocketAddress("localhost", 2048));
            }
            
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                if (connectFuture.isDone()) {
                    
                }
            }
        });
        
        ChannelFuture f = server.bind(2048);
        f.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    
                } else {
                    
                }
            }
            
        });
    }
}
