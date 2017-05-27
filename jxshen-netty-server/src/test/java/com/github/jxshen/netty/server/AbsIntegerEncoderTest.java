package com.github.jxshen.netty.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.jxshen.netty.server.handler.AbsIntegerEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeInt(-1 * i);
        }
        
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(new Integer(i), channel.readOutbound());
        }
        Assert.assertNull(channel.readOutbound());
    }
}
