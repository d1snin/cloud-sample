package xyz.d1snin.client.internal.managers;

import lombok.NonNull;
import xyz.d1snin.client.api.CloudClient;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.Response;

import java.util.concurrent.*;

public class RequestManagerImpl implements RequestManager {

  private final ExecutorService executor = Executors.newCachedThreadPool();
  private final CopyOnWriteArrayList<Response> responses = new CopyOnWriteArrayList<>();
  private final CloudClient cloudClient;

  public RequestManagerImpl(CloudClient cloudClient) {
    this.cloudClient = cloudClient;
  }

  @Override
  public Future<Response> submitRequest(@NonNull ServerRequest request) {
    return executor.submit(
        () -> {
          long requestId = ThreadLocalRandom.current().nextInt(100000000, 999999999 + 1);

          request.setRequestId(requestId);
          request.setAuthenticationToken(cloudClient.getAuthenticationToken());

          try {
            cloudClient.getChannel().writeAndFlush(request).sync();
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
