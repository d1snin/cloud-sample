package xyz.d1snin.commons.server_requests;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_responses.Response;

import java.io.Serializable;

@Data
public abstract class ServerRequest implements Serializable {

  private ChannelHandlerContext ctx;
  private long requestId;
  private String authenticationToken;

  public abstract void execute(CloudServer server, ChannelHandlerContext ctx);

  public void sendResponse(int responseCode, Object content) {
    Response response = new Response(responseCode, content);
    response.setRequestId(requestId);
    ctx.writeAndFlush(response);
  }

  public void sendResponse(int responseCode) {
    sendResponse(responseCode, null);
  }
}
