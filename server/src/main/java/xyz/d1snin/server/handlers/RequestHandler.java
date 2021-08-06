package xyz.d1snin.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_requests.Unauthorized;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.utils.SocketAddressUtils;

@Slf4j
public class RequestHandler extends SimpleChannelInboundHandler<ServerRequest> {

  private final CloudServer server;

  public RequestHandler(CloudServer server) {
    this.server = server;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {
    log.info("handled: {} {}", msg, msg.getClass());
    msg.setCtx(ctx);

    if (!(msg instanceof Unauthorized)
        && (server
                    .getClientSessionsManager()
                    .getSession(SocketAddressUtils.getAddressString(ctx.channel().remoteAddress()))
                == null
            || server.getCloud().getUsers().stream()
                .noneMatch(
                    it -> msg.getAuthenticationToken().equals(it.getAuthenticationToken())))) {

      msg.sendResponse(ResponseCodes.UNAUTHORIZED);
      return;
    }

    msg.execute(server, ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
    throwable.printStackTrace();
  }
}
