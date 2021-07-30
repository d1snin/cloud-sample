package xyz.d1snin.commons.server_requests.user;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.user.UserIdData;
import xyz.d1snin.commons.utils.SocketAddressUtils;

import java.io.Serializable;

public class UserIdRequest extends ServerRequest implements Serializable {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    sendResponse(
        ResponseCodes.OK_CODE,
        new UserIdData(
            server
                .getClientSessionsManager()
                .getSession(SocketAddressUtils.getAddressString(ctx.channel().remoteAddress()))
                .getUser()
                .getUserId()));
  }
}
