package xyz.d1snin.server.shared.server_requests;

import io.netty.channel.ChannelHandlerContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import xyz.d1snin.server.shared.server_responses.Response;
import xyz.d1snin.server.shared.server_responses.ResponseType;
import xyz.d1snin.server.api.CloudServer;

@EqualsAndHashCode
public abstract class ServerRequest {

  @Getter @Setter private ChannelHandlerContext ctx;

  public abstract void execute(CloudServer server);

  protected void sendResponse(String text, ResponseType type) {
    Response response = new Response(text, type);
    response.setRequest(this);

    ctx.writeAndFlush(response);
  }
}
