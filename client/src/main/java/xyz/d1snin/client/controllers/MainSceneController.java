package xyz.d1snin.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDate;

public class MainSceneController {

  @FXML private TextArea logs_area;

  @FXML
  private void upload() throws IOException {
    JFileChooser chooser = new JFileChooser();
    int val = chooser.showOpenDialog(null);

    if (val == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 1569));
      FileInputStream fis = new FileInputStream(file);
      StringBuilder bytes = new StringBuilder();

      while (true) {
        int read = fis.read();

        if (read == -1) {
          break;
        }

        bytes.append(read).append(" ");
      }

      channel.write(
          ByteBuffer.wrap(
              String.format("upload %s %s %s", file.getName(), "394057413", bytes.toString().trim())
                  .getBytes()));
      log("File " + file + " was uploaded.");

      channel.close();
    }
  }

  private void log(String content) {
    logs_area.appendText("[" + LocalDate.now() + "] " + content);
  }
}
