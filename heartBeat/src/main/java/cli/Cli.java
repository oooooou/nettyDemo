package cli;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Cli {
    public static  void main(String args[]){
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.channel(NioSocketChannel.class)
                    .group(workGroup)
                    .handler( new ClientHandlerIni()) ;
            ChannelFuture f= b.connect("127.0.0.1",8085).syncUninterruptibly();

        }finally {
             workGroup.shutdownGracefully();
        }
    }
}
