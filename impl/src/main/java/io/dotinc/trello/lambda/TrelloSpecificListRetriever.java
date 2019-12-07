package io.dotinc.trello.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.dotinc.core.ResponseWrapper;
import io.dotinc.trello.request.TrelloSpecificListRequest;
import io.dotinc.trello.response.GatewayResponse;
import io.dotinc.trello.model.TrelloList;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.inject.Named;


/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
@Named("trelloSpecificList")
public class TrelloSpecificListRetriever extends AbstractTrelloBoardListRetriever implements RequestHandler<TrelloSpecificListRequest, APIGatewayProxyResponseEvent> {

    @SneakyThrows
    @Override
    public APIGatewayProxyResponseEvent handleRequest(TrelloSpecificListRequest input, Context context) {
        log.info("Got requested with body {}", input.toString());

        ResponseWrapper<TrelloList> trelloResponse = listService.getSpecificListByBoardAndList(input.getBody().getBoardId(), input.getBody().getListName());
        TrelloList trelloList = boardService.convertSpecificListByBoardAndList(trelloResponse);
        GatewayResponse response =  boardService.buildGatewayResponse(trelloResponse, trelloList);

        return new APIGatewayProxyResponseEvent()
                .withBody(objectMapper.writeValueAsString(response.getBody()))
                .withHeaders(response.getHeaders())
                .withStatusCode(response.getStatusCode());
    }
}
