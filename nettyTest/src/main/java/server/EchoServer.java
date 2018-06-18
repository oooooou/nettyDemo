package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private  final int port;
    public EchoServer(int port){
        this.port=port;
    }


    public static void main(String args[]) throws Exception{
        if(args.length!=1){
            System.err.println("usage"+EchoServer.class.getSimpleName()+"<port>" );
            return;
        };

        int port=Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
    public void start() throws Exception{
        final EchoServerHandler serverHandler=new EchoServerHandler();
        final TimeSerHandler    timeSerHandler=new TimeSerHandler();
        final TimeEncoder    timeEncoder=new TimeEncoder();
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel)
                                    throws Exception {
                                    socketChannel.pipeline().addLast(timeEncoder).addLast(timeSerHandler) ;
                                }
                            }
                    );
            ChannelFuture f=b.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
