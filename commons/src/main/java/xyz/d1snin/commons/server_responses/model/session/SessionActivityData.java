package xyz.d1snin.commons.server_responses.model.session;

import lombok.Data;

import java.io.Serializable;

@Data
public class SessionActivityData implements Serializable {
  private final boolean active;
}
