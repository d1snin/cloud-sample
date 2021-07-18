package xyz.d1snin.server.internal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;

@Slf4j
public class RequestHandler extends SimpleChannelInboundHandler<ServerRequest> {

  private final CloudServer server;

  public RequestHandler(CloudServer server) {
    this.server = server;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    ctx.pipeline()
        .get(SslHandler.class)
        .handshakeFuture()
        .addListener(
            it ->
                log.debug(
                    "A new connection from a remote host {}, Session is protected by {} cipher suite.",
                    ctx.channel().remoteAddress(),
                    ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite()));
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {
    msg.setCtx(ctx);
    msg.execute(server, ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
    throwable.printStackTrace();
  }
}
