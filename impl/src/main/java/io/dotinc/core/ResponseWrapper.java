package io.dotinc.core;

import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
public class ResponseWrapper<T> {

    Response response;

    public ResponseWrapper(Response response) {
        this.response = Response.fromResponse(response).build();
    }

    public Response getResponse() {
        return response;
    }
}
