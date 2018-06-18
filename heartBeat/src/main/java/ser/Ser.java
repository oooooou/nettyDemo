package ser;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class Ser {
    public static void main(String args[]){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap b  ;
        try{
            b=new ServerBootstrap();
            b.group(bossGroup,workGroup)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SerHandlerIni());
            ChannelFuture f=b.bind("127.0.0.1",8085).syncUninterruptibly();
            f.channel().closeFuture().syncUninterruptibly();
        }finally {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workGroup.shutdownGracefully().syncUninterruptibly();
        }

    }
}
