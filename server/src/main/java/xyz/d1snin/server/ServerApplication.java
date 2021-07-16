package xyz.d1snin.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.CloudBuilder;
import xyz.d1snin.server.api.CloudServerBuilder;
import xyz.d1snin.server.commands.PingCommand;
import xyz.d1snin.server.commands.cloud.UploadFileCommand;
import xyz.d1snin.server.commands.fsmanagement.*;
import xyz.d1snin.server.listeners.ServerEnabledListener;
import xyz.d1snin.server.listeners.SocketConnectionListener;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServerApplication {

  public static void main(String[] args) {
    Logger log = LoggerFactory.getLogger(ServerApplication.class);

    Cloud cloud;

    try {
      cloud = new CloudBuilder().buildCloudInstance();
    } catch (UnknownHostException e) {
      log.error("Something went wrong while trying to connect to the database!");
      e.printStackTrace();
      return;
    }

    try {
      new CloudServerBuilder()
          .setPort(1569)
          .setEventListeners(
              // events
              new ServerEnabledListener(),
              new SocketConnectionListener(),
              // commands
              new UploadFileCommand(),
              new CatCommand(),
              new CdCommand(),
              new LsCommand(),
              new MkdirCommand(),
              new PwdCommand(),
              new TouchCommand(),
              new PingCommand())
          .setCloud(cloud)
          .buildServerInstance();
    } catch (IOException e) {
      log.error("An error occurred while trying to start the server!");
      e.printStackTrace();
    }
  }
}
