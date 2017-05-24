package com.github.jxshen.netty.server.buf;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ByteBufAllocatorTest {
    
    public static final int PORT = 8080;

    public static void main(String[] args) {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(PORT))
        .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipe = ch.pipeline();
                ByteBufAllocator alloc0 = ch.alloc();
                pipe.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) {
                        ByteBufAllocator alloc1 = ctx.alloc();
                    }
                });
            }
        });
    }
}
