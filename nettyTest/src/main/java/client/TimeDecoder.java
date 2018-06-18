package client;

import bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if(byteBuf.readableBytes()<8)
            return;
        Long a=byteBuf.readLong();
        System.out.println(a);
        list.add(new UnixTime(a) );
    }
}
