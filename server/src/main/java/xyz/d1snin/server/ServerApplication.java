package xyz.d1snin.server;

import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.CloudBuilder;
import xyz.d1snin.server.api.CloudServerBuilder;

import java.net.UnknownHostException;

@Slf4j
public class ServerApplication {

  public static void main(String[] args) {

    Cloud cloud;

    try {
      cloud = new CloudBuilder().buildCloudInstance();
    } catch (UnknownHostException e) {
      log.error("Something went wrong while trying to connect to the database!");
      e.printStackTrace();
      return;
    }

    new CloudServerBuilder().setPort(1569).setCloud(cloud).buildServerInstance();
  }
}
