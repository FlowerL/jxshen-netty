package com.github.jxshen.netty.server.udp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * reference from: netty in action
 */
public class LogEventBroadCaster {

    private final EventLoopGroup group;
    private final Bootstrap b;
    private final File file;
    
    public LogEventBroadCaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group).channel(NioDatagramChannel.class)
        .option(ChannelOption.SO_BROADCAST, true).handler(new LogEventEncoder(address));
        this.file = file;
    }
    
    public void run() throws IOException {
        Channel ch = b.bind(0).syncUninterruptibly().channel();
        long pointer = 0;
        for (;;) {
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            } else {
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);
                String line;
                while ((line = raf.readLine()) != null) {
                    ch.write(new LogEvent(null, file.getAbsolutePath(), line, -1L));
                }
                ch.flush();
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }
    
    public void stop() {
        group.shutdownGracefully();
    }
    
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("255.255.255.255", 4991);
        String path = System.getProperty("user.dir") + "/log.txt";
        LogEventBroadCaster broadcaster = new LogEventBroadCaster(address, new File(path));
        try {
            broadcaster.run();
        } finally {
            broadcaster.stop();
        }
    }
    
}