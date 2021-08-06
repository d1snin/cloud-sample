package xyz.d1snin.commons.server_requests.files;

import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;
import xyz.d1snin.commons.server.CloudServer;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.ResponseCodes;
import xyz.d1snin.commons.server_responses.model.files.UserFilesData;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class UserFilesListRequest extends ServerRequest implements Serializable {

  @SneakyThrows
  @Override
  public void execute(CloudServer server, ChannelHandlerContext ctx) {
    sendResponse(
        ResponseCodes.OK_CODE,
        new UserFilesData(
            Files.walk(
                    server
                        .getCloud()
                        .getUserByToken(getAuthenticationToken())
                        .getUserStorage()
                        .getPath())
                .filter(it -> !Files.isDirectory(it))
                .map(it -> it.getFileName().toString())
                .collect(Collectors.toList())));
  }
}
