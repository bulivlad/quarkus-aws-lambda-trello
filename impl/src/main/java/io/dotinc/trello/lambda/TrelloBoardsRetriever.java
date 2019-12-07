package io.dotinc.trello.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.dotinc.core.ResponseWrapper;
import io.dotinc.trello.request.TrelloBoardRequest;
import io.dotinc.trello.response.GatewayResponse;
import io.dotinc.trello.model.TrelloBoard;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import javax.inject.Named;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
@Named("trelloBoards")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrelloBoardsRetriever extends AbstractTrelloBoardListRetriever implements RequestHandler<TrelloBoardRequest, APIGatewayProxyResponseEvent> {

    @SneakyThrows
    @Override
    public APIGatewayProxyResponseEvent handleRequest(TrelloBoardRequest input, Context context) {
        log.info("Got requested with body {}", input.toString());

        ResponseWrapper<TrelloBoard> trelloResponse = boardService.getBoardById(input.getBoardId());

        TrelloBoard trelloBoard = boardService.convertBoardByIdResponse(trelloResponse);
        GatewayResponse response = boardService.buildGatewayResponse(trelloResponse, trelloBoard);

        return new APIGatewayProxyResponseEvent()
                .withBody(objectMapper.writeValueAsString(response.getBody()))
                .withHeaders(response.getHeaders())
                .withStatusCode(response.getStatusCode());
    }
}
