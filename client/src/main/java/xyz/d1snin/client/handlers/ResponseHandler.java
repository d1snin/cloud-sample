package xyz.d1snin.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.commons.server_responses.Response;

public class ResponseHandler extends SimpleChannelInboundHandler<Response> {

  private final RequestManager requestManager;

  public ResponseHandler(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Response msg) {
    requestManager.submitResponse(msg);
  }
}
