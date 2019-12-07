package io.dotinc.trello.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.dotinc.core.ResponseWrapper;
import io.dotinc.trello.request.TrelloCardsRequest;
import io.dotinc.trello.response.GatewayResponse;
import io.dotinc.trello.model.TrelloCard;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.inject.Named;
import java.util.List;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
@Named("trelloCards")
public class TrelloCardsRetriever extends AbstractTrelloBoardListRetriever implements RequestHandler<TrelloCardsRequest, APIGatewayProxyResponseEvent> {

    @SneakyThrows
    @Override
    public APIGatewayProxyResponseEvent handleRequest(TrelloCardsRequest input, Context context) {
        log.info("Got requested with body {}", input.toString());

        ResponseWrapper<List<TrelloCard>> trelloResponse = listService.getCardsByList(input.getListId());
        List<TrelloCard> trelloList = boardService.convertCardsByList(trelloResponse);
        GatewayResponse response =  boardService.buildGatewayResponseForTrelloCards(trelloResponse, trelloList);

        return new APIGatewayProxyResponseEvent()
                .withBody(objectMapper.writeValueAsString(response.getBody()))
                .withHeaders(response.getHeaders())
                .withStatusCode(response.getStatusCode());
    }

}
