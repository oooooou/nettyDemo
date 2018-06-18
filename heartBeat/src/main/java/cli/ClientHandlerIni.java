package cli;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientHandlerIni extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel)throws Exception {
        socketChannel.pipeline()
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new CliHeartHandler());
    }
}
