package xyz.d1snin.commons.server_responses.model.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationData implements Serializable {
  private final String authenticationToken;
}
