package xyz.d1snin.commons.server_requests.session;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;

import java.io.Serializable;

public class SessionRegistrationRequest extends ServerRequest implements Serializable {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    server
        .getClientSessionsManager()
        .registerNewSession(
            ctx.channel().remoteAddress(),
            server.getCloud().getUserByToken(getAuthenticationToken()));

    sendResponse(ResponseCodes.OK_CODE);
  }
}
