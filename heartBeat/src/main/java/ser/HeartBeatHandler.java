package ser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event=(IdleStateEvent) evt;
            if(event == IdleStateEvent.WRITER_IDLE_STATE_EVENT){
                ByteBuf b=Unpooled.copiedBuffer("asd",CharsetUtil.UTF_8);
                ctx.pipeline().writeAndFlush ( b);
            }
        }
        else {
        super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ByteBuf b=Unpooled.copiedBuffer("asd",CharsetUtil.UTF_8);
        ctx.pipeline().writeAndFlush ( b);
        //ReferenceCountUtil.release(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
