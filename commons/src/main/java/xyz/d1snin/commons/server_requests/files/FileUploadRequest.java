package xyz.d1snin.commons.server_requests.files;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.cloud.api.Storage;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploadRequest extends ServerRequest implements Serializable {

  private final byte[] bytes;
  private final String filePath;

  public FileUploadRequest(byte[] bytes, String filePath) {
    this.bytes = bytes;
    this.filePath = filePath;
  }

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    Storage storage = server.getCloud().getUserByToken(getAuthenticationToken()).getUserStorage();

    Path file;
    try {
      file = storage.createFile(storage.resolvePath(filePath));
    } catch (IllegalArgumentException e) {
      sendResponse(ResponseCodes.FILE_ALREADY_EXISTS);
      return;
    }

    try {
      Files.write(file, bytes);
    } catch (IOException e) {
      e.printStackTrace();
      sendResponse(ResponseCodes.UPLOADING_FAILED);
    }

    sendResponse(ResponseCodes.OK_CODE);
  }
}
