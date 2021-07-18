package xyz.d1snin.commons.server_responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Response {
  private final int responseCode;
  private final String content;

  @Setter private long requestId;
}
