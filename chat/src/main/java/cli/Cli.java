package cli;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Cli {
    public static  void main(String args[]){
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.channel(NioSocketChannel.class)
                    .group(workGroup)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new CliChatHandler( ));
                        }
                    }) ;
            ChannelFuture f= b.connect("127.0.0.1",8085).syncUninterruptibly();
            f.channel().closeFuture().syncUninterruptibly();
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
