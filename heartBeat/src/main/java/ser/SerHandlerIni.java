package ser;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class SerHandlerIni extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
          .addLast(new IdleStateHandler(0,4,0,TimeUnit.SECONDS))

                .addLast(new StringDecoder())
                .addLast(new HeartBeatHandler());
    }
}
