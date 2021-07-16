package xyz.d1snin.server.commands.cloud;

import xyz.d1snin.server.commands.ServerCommand;
import xyz.d1snin.server.commands.entities.Argument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadFileCommand extends ServerCommand {

  public UploadFileCommand() {
    this.usage = "upload";

    exec(
        e -> {
          File file;
          try {
            file =
                e.getServer()
                    .getCloud()
                    .getUserById(getArgVal(1))
                    .getUserStorage()
                    .createFile(getArgVal(0));
          } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
          }

          FileOutputStream fos;
          try {
            fos = new FileOutputStream(file);
          } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return;
          }

          for (String s : getArgVal(2).split("\\s|\n+")) {
            try {
              fos.write(Byte.parseByte(s));
            } catch (IOException ioException) {
              ioException.printStackTrace();
            }
          }
        },
        new Argument(null, "<File Name>", true, false),
        new Argument(null, "<User Id>", true, false),
        new Argument(null, "<Bytes>", true, true));
  }
}
