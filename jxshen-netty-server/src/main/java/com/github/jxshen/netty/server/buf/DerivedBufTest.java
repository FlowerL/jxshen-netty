package com.github.jxshen.netty.server.buf;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * reference from: netty in action
 */
public class DerivedBufTest {

    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 14);
        ByteBuf copy = buf.copy(0, 14);
        System.out.println(buf.toString(utf8));
        System.out.println(sliced.toString(utf8));
        System.out.println(copy.toString(utf8));
    }
}
