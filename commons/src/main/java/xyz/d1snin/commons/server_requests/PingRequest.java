package xyz.d1snin.commons.server_requests;

import io.netty.channel.ChannelHandlerContext;
import lombok.Builder;
import lombok.NoArgsConstructor;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_responses.ResponseCodes;

@Builder
@NoArgsConstructor
public class PingRequest extends ServerRequest {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    sendResponse(ResponseCodes.OK_CODE, "pong!");
  }
}
