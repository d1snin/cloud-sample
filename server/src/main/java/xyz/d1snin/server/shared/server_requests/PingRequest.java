package xyz.d1snin.server.shared.server_requests;

import lombok.Builder;
import xyz.d1snin.server.shared.server_responses.ResponseType;
import xyz.d1snin.server.api.CloudServer;

@Builder
public class PingRequest extends ServerRequest {

  @Override
  public void execute(CloudServer server) {
    sendResponse("pong", ResponseType.SUCCESS);
  }
}
