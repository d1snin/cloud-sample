package xyz.d1snin.server.shared.server_requests;

import lombok.Builder;
import xyz.d1snin.server.api.CloudServer;
import xyz.d1snin.server.shared.server_responses.ResponseCodes;
import xyz.d1snin.server.shared.server_responses.ResponseType;

@Builder
public class PingRequest extends ServerRequest {

  @Override
  public void execute(CloudServer server) {
    sendResponse(ResponseCodes.OK_CODE, ResponseType.SUCCESS);
  }
}
