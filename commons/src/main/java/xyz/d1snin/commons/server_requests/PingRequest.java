package xyz.d1snin.commons.server_requests;

import io.netty.channel.ChannelHandlerContext;
import lombok.Builder;
import lombok.NoArgsConstructor;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.PingData;

import java.io.Serializable;

@Builder
@NoArgsConstructor
public class PingRequest extends ServerRequest implements Serializable {

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    sendResponse(
        ResponseCodes.OK_CODE,
        new PingData("Pong!" /* should be replaced with actual ping on client-side */));
  }
}
