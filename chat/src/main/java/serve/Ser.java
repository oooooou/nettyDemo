package serve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new SerChatHandler());
                        }
                    });
            ChannelFuture f=b.bind("127.0.0.1",8085).syncUninterruptibly();
            f.channel().closeFuture().syncUninterruptibly();
        }finally {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workGroup.shutdownGracefully().syncUninterruptibly();
        }

    }
}
