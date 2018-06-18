package serv;

import DB.JdbcOp;
import bean.Event;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import javax.swing.text.AbstractDocument;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class HttpSerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest=(FullHttpRequest) msg;
        String content=fullHttpRequest.content().toString(CharsetUtil.UTF_8);
        System.out.println(content);
        ((FullHttpRequest) msg).release();

        if(content.equals("11234")){
            ctx.pipeline().addLast(HttpServIni.group,new jdbcHandler() );
            ctx.fireUserEventTriggered(new Event() );
        }
        else {
            ByteBuf buf = Unpooled.copiedBuffer( content, CharsetUtil.UTF_8);
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
            ctx.channel().writeAndFlush(fullHttpResponse);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
