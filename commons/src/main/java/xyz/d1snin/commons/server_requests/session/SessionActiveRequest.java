package xyz.d1snin.commons.server_requests.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import xyz.d1snin.commons.model.ClientSession;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.session.SessionActivityData;
import xyz.d1snin.commons.utils.SocketAddressUtils;

import java.io.Serializable;

@RequiredArgsConstructor
public class SessionActiveRequest extends ServerRequest implements Serializable {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    ClientSession session =
        server
            .getClientSessionsManager()
            .getSession(SocketAddressUtils.getAddressString(ctx.channel().remoteAddress()));

    if (session == null) {
      sendResponse(ResponseCodes.OK_CODE, new SessionActivityData(false));

    } else {
      sendResponse(ResponseCodes.OK_CODE, new SessionActivityData(session.isActive()));
    }
  }
}
