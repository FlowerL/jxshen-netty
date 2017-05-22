package com.github.jxshen.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TCPServer {
    
    public static void main(String[] args) {
        startTCP();
    }

    public static boolean startTCP() {
        boolean isStart = false;
        ServerBootstrap bootstrap = null;
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            bootstrap = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer(){
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//                    pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//                    pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                    pipeline.addLast(new TcpServerHandler());
                }
            });
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture cf = bootstrap.bind("localhost", 8080).sync();
            isStart = true ;
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        return isStart;
    }
}
