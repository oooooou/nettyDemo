package serve;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SerChatHandler extends ChannelInboundHandlerAdapter {
    public static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(Unpooled.copiedBuffer("[SERVER] - " +  incoming.remoteAddress()+ " 加入\n" ,CharsetUtil.UTF_8));
        }
        channelGroup.add(ctx.channel());
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(Unpooled.copiedBuffer("[SERVER] - " +  incoming.remoteAddress()+ " 离开\n",CharsetUtil.UTF_8) );
        }
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(Unpooled.copiedBuffer("[SERVER] - " +  incoming.remoteAddress()+ " 在线\n" ,CharsetUtil.UTF_8));
        }
        channelGroup.add(ctx.channel());
    }
}
