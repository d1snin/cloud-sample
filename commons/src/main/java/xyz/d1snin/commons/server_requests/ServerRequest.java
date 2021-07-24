package xyz.d1snin.commons.server_requests;

import io.netty.channel.ChannelHandlerContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_responses.Response;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public abstract class ServerRequest implements Serializable {

  private ChannelHandlerContext ctx;
  private long requestId;

  public abstract void execute(CloudServer server, ChannelHandlerContext ctx);

  protected void sendResponse(int responseCode, String content) {
    Response response = new Response(responseCode, content);
    response.setRequestId(requestId);
    ctx.writeAndFlush(response);
  }
}
