package xyz.d1snin.commons.server_responses;

import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
public class Response implements Serializable {
  private final int responseCode;
  private final Object content;

  @Setter private long requestId;
}
