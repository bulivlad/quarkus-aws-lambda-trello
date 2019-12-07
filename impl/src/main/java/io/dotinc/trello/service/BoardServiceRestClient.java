package io.dotinc.trello.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Singleton
@RegisterRestClient
public interface BoardServiceRestClient {

    @GET
    @Path("/{boardId}/lists")
    Response getListsByBoard(@PathParam String boardId, @QueryParam("key") String key, @QueryParam("token") String token);

    @GET
    @Path("/{boardId}")
    Response  getBoardById(@PathParam String boardId, @QueryParam("key") String key, @QueryParam("token") String token);

}
