package xyz.d1snin.client.internal.managers;

import io.netty.channel.Channel;
import lombok.NonNull;
import xyz.d1snin.client.api.managers.RequestManager;
import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.Response;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class RequestManagerImpl implements RequestManager {

  private final ExecutorService executor;
  private final CopyOnWriteArrayList<Response> responses;
  private final Channel channel;

  public RequestManagerImpl(@NonNull Channel channel) {
    executor = Executors.newCachedThreadPool();
    responses = new CopyOnWriteArrayList<>();
    this.channel = channel;
  }

  @Override
  public Future<Response> submitRequest(@NonNull ServerRequest request) {
    return executor.submit(
        () -> {
          long requestId = ThreadLocalRandom.current().nextInt(100000000, 999999999 + 1);

          request.setRequestId(requestId);
          channel.writeAndFlush(request);

          AtomicReference<Response> response = new AtomicReference<>();

          do {
            responses.forEach(
                it -> {
                  if (it.getRequestId() == requestId) {
                    response.set(it);
                  }
                });
          } while (response.get() == null);

          return response.get();
        });
  }

  @Override
  public void submitResponse(@NonNull Response response) {
    responses.add(response);
  }
}
