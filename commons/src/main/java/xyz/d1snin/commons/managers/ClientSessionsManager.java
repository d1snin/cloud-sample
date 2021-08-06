package xyz.d1snin.commons.managers;

import xyz.d1snin.cloud.api.User;
import xyz.d1snin.commons.model.ClientSession;

import java.net.SocketAddress;

public interface ClientSessionsManager {

  ClientSession registerNewSession(SocketAddress address, User user);

  ClientSession getSession(String address);
}
