package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in =(ByteBuf) msg;
        System.out.println("server receiverd:"+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf =ctx.alloc().buffer(8);
        buf.writeInt(2);
        buf.writeByte(44);
        ctx.write(buf);

     ctx.writeAndFlush(Unpooled.copiedBuffer("Nroc!",
            CharsetUtil.UTF_8));
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
