package serv;

import DB.JdbcOp;
import bean.Event;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class jdbcHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         if(evt instanceof Event){
             int temp=JdbcOp.getAll();
             ByteBuf buf = Unpooled.copiedBuffer("ser received\nmax asd is " + String.valueOf(temp), CharsetUtil.UTF_8);
             FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
             fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                     .set(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
             ctx.channel().writeAndFlush(fullHttpResponse);
         }
    }
}
