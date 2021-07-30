package xyz.d1snin.commons.model;

import lombok.Data;
import lombok.Setter;
import xyz.d1snin.cloud.api.User;

import java.net.SocketAddress;

@Data
@Setter
public class ClientSession {
  private final SocketAddress address;
  private final User user;
  private boolean active = false;
}
