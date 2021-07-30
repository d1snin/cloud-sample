package xyz.d1snin.client.internal.managers;

import io.netty.channel.Channel;
import lombok.NonNull;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.Response;

import java.util.concurrent.*;

public class RequestManagerImpl implements RequestManager {

  private final ExecutorService executor = Executors.newCachedThreadPool();
  private final CopyOnWriteArrayList<Response> responses = new CopyOnWriteArrayList<>();
  private final Channel channel;

  public RequestManagerImpl(Channel channel) {
    this.channel = channel;
  }

  @Override
  public Future<Response> submitRequest(@NonNull ServerRequest request) {
    return executor.submit(
        () -> {
          long requestId = ThreadLocalRandom.current().nextInt(100000000, 999999999 + 1);

          request.setRequestId(requestId);

          try {
            channel.writeAndFlush(request).sync();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          Response response = null;

          do {
            for (Response res : responses) {
              if (res.getRequestId() == requestId) {
                response = res;
              }
            }
          } while (response == null);

          return response;
        });
  }

  @Override
  public void submitResponse(@NonNull Response response) {
    responses.add(response);
  }
}
