package xyz.d1snin.server.internal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
  protected void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {
    log.info("handled: {}", msg);
    msg.setCtx(ctx);
    msg.execute(server, ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
    throwable.printStackTrace();
  }
}
