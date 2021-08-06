package xyz.d1snin.server.internal.managers;

import xyz.d1snin.cloud.api.User;
import xyz.d1snin.commons.managers.ClientSessionsManager;
import xyz.d1snin.commons.model.ClientSession;
import xyz.d1snin.commons.utils.SocketAddressUtils;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientSessionManagerImpl implements ClientSessionsManager {

  private final List<ClientSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public ClientSession registerNewSession(SocketAddress address, User user) {
    ClientSession session = new ClientSession(address, user);
    session.setActive(true);
    sessions.add(session);
    return session;
  }

  @Override
  public ClientSession getSession(String address) {
    return sessions.stream()
        .filter(it -> address.equals(SocketAddressUtils.getAddressString(it.getAddress())))
        .findFirst()
        .orElse(null);
  }
}
