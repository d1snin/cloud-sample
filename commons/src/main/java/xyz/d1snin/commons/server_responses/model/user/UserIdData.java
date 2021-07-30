package xyz.d1snin.commons.server_responses.model.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserIdData implements Serializable {
  private final String userId;
}
