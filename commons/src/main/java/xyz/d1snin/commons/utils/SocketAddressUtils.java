package xyz.d1snin.commons.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SocketAddressUtils {
  public static String getAddressString(SocketAddress address) {
    return ((InetSocketAddress) address).getAddress().toString();
  }
}
