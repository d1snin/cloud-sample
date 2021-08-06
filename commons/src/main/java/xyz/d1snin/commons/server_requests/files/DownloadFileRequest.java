package xyz.d1snin.commons.server_requests.files;

import io.netty.channel.ChannelHandlerContext;
import xyz.d1snin.cloud.api.Storage;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.files.FileData;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadFileRequest extends ServerRequest implements Serializable {

  private final String filePath;

  public DownloadFileRequest(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    Storage storage = server.getCloud().getUserByToken(getAuthenticationToken()).getUserStorage();
    Path resolvedPath = storage.resolvePath(filePath);

    if (!Files.exists(resolvedPath)) {
      sendResponse(ResponseCodes.REQUESTED_FILE_DOES_NOT_EXIST);
      return;
    }

    try {
      sendResponse(
          ResponseCodes.OK_CODE,
          new FileData(
              Files.readAllBytes(resolvedPath), resolvedPath.getFileName().toString()));
    } catch (IOException e) {
      sendResponse(ResponseCodes.DOWNLOADING_FAILED);
      e.printStackTrace();
    }
  }
}
