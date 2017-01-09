package com.github.jxshen.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        ctx.flush();
//        ctx.writeAndFlush(msg);
        
//        ByteBuf in = (ByteBuf)msg;
//        try {
//            while (in.isReadable()) {
//                System.out.print((char)in.readByte());
//                System.out.flush();
//            }
////            System.out.print(in.toString(CharsetUtil.US_ASCII));
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
