package xyz.d1snin.commons.server_requests.files;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.cloud.api.Storage;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class FileUploadRequest extends ServerRequest implements Serializable {

  private final byte[] bytes;
  private final String fileName;
  private final String userId;

  public FileUploadRequest(byte[] bytes, String fileName, String userId) {
    this.bytes = bytes;
    this.fileName = fileName;
    this.userId = userId;
  }

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    // TODO: make it more resistant to errors
    Storage storage = server.getCloud().getUserById(userId).getUserStorage();

    try {
      Files.write(storage.createFile(storage.resolvePath(fileName)), bytes);
    } catch (IOException e) {
      e.printStackTrace();
      sendResponse(ResponseCodes.UPLOADING_FAILED);
    }

    sendResponse(ResponseCodes.OK_CODE);
  }
}
