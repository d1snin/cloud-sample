package xyz.d1snin.server;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import xyz.d1snin.cloud.api.Cloud;
import xyz.d1snin.cloud.api.CloudBuilder;
import xyz.d1snin.server.api.CloudServerBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    String keyPass;
    try {
      keyPass =
          JsonParser.parseReader(new FileReader("ssl_configuration.json"))
              .getAsJsonObject()
              .get("ssl_key")
              .getAsString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    try {
      new CloudServerBuilder()
          .setPort(1569)
          .setCloud(cloud)
          .setKeyStore(
              Thread.currentThread().getContextClassLoader().getResourceAsStream("server.jks"))
          .setSslKeyPass(keyPass)
          .buildServerInstance();
    } catch (IOException e) {
      log.error("An error occurred while trying to start the server!");
      e.printStackTrace();
    }
  }
}
