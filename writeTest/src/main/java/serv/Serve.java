package serv;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;


public class Serve {
    private String ip;
    int port;
    Boolean ssl;
    ServerBootstrap b;
    public Serve (String ip,int port,Boolean ssl){
        this.ip=ip;
        this.port=port;
        this.ssl=ssl;
    }
    public void start()   {
        if(ssl){
            SSLHandlerProvider.initSSLContext();
        }
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{

            b=new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG,1024);
            b.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServIni(ssl));
            ChannelFuture f=b.bind(port).syncUninterruptibly();
            f.channel().closeFuture().syncUninterruptibly();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
