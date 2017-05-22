package com.github.jxshen.netty.hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * reference from: http://www.cnblogs.com/zou90512/p/3492878.html
 */
public class NettyHelloClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b.group(group).channel(NioSocketChannel.class).handler(new HelloClientInitializer());
            
            Channel ch = b.connect(HOST, PORT).sync().channel();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for(;;) {
               String line = in.readLine(); 
               if (line == null) {
                   continue;
               }
               // add "\n" to adapt the delimiter decoder at server side
               ch.writeAndFlush(line + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }
        
    }
}
