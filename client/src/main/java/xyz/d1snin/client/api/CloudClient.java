package xyz.d1snin.client.api;

import javafx.stage.Stage;
import xyz.d1snin.client.utils.storage.AppStorage;

public interface CloudClient {

  Stage getStage();

  AppStorage getAppStorage();

  void launch();
}
