package xyz.d1snin.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.commons.server_responses.Response;

@Slf4j
public class ResponseHandler extends SimpleChannelInboundHandler<Response> {

  private final CloudClient cloudClient;

  public ResponseHandler(CloudClient cloudClient) {
    this.cloudClient = cloudClient;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Response msg) {
    log.info("handled: {}", msg);
    cloudClient.getRequestManager().submitResponse(msg);
  }
}
