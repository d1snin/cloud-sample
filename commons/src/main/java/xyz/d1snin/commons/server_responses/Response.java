package xyz.d1snin.commons.server_responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Response implements Serializable {
  private final int responseCode;
  private final Object content;

  @Setter private long requestId;
}
