package xyz.d1snin.commons.server_requests.auth;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.commons.model.ClientSession;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_requests.Unauthorized;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.auth.AuthenticationData;
import xyz.d1snin.commons.utils.SocketAddressUtils;

import java.io.Serializable;

public class AuthenticationTokenRequest extends ServerRequest
    implements Serializable, Unauthorized {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    ClientSession session =
        server
            .getClientSessionsManager()
            .getSession(SocketAddressUtils.getAddressString(ctx.channel().remoteAddress()));

    sendResponse(
        session == null ? ResponseCodes.OK_CODE : ResponseCodes.SESSION_IS_NOT_ACTIVE,
        new AuthenticationData(
            session == null ? null : session.getUser().getAuthenticationToken()));
  }
}
