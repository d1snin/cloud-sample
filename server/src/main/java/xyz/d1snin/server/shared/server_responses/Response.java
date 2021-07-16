package xyz.d1snin.server.shared.server_responses;

import lombok.Data;
import lombok.Setter;
import xyz.d1snin.server.shared.server_requests.ServerRequest;

@Data
public class Response {
  private final int responseCode;
  private final ResponseType responseType;

  @Setter private ServerRequest request;
}
