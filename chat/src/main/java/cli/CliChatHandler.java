package cli;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class CliChatHandler  extends SimpleChannelInboundHandler  {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        String s = ((ByteBuf)o  ).toString (CharsetUtil.UTF_8);
        System.out.println(s);
    }
}
