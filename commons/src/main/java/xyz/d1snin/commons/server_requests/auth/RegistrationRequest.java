package xyz.d1snin.commons.server_requests.auth;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import xyz.d1snin.cloud.api.User;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;

import java.io.Serializable;

@RequiredArgsConstructor
public class RegistrationRequest extends ServerRequest implements Serializable {

  private final String login;
  private final String password;

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    try {
      User user = server.getCloud().createNewUser(login, password);
      sendResponse(ResponseCodes.OK_CODE, user.getUserId());
    } catch (IllegalArgumentException e) {
      sendResponse(ResponseCodes.LOGIN_IS_BUSY, e.getMessage());
    }
  }
}
