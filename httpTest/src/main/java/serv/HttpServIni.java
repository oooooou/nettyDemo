package serv;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.net.ssl.SSLEngine;
import java.awt.*;

public class HttpServIni extends ChannelInitializer<SocketChannel> {
    public  static  final EventExecutorGroup group =new DefaultEventExecutorGroup(16);
    Boolean ss;
    public HttpServIni( Boolean ss){
        this.ss=ss;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        if(ss){
            socketChannel.pipeline()
                    .addFirst("SSL",SSLHandlerProvider.getSSLHandler());
        }
        socketChannel.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(65536))
                .addLast(new HttpSerHandler());
    }
}
