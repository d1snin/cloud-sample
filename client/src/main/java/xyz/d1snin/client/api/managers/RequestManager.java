package xyz.d1snin.client.api.managers;

import xyz.d1snin.commons.server_requests.ServerRequest;
import xyz.d1snin.commons.server_responses.Response;

import java.util.concurrent.Future;

public interface RequestManager {

  Future<Response> submitRequest(ServerRequest request);

  void submitResponse(Response response);
}
