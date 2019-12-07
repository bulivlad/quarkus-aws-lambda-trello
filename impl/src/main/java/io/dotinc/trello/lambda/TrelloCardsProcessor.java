package io.dotinc.trello.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.dotinc.trello.request.TrelloCardProcessorRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.inject.Named;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
@Named("trelloCardProcessor")
public class TrelloCardsProcessor extends AbstractTrelloBoardListRetriever implements RequestHandler<TrelloCardProcessorRequest, APIGatewayProxyResponseEvent> {
    @SneakyThrows
    @Override
    public APIGatewayProxyResponseEvent handleRequest(TrelloCardProcessorRequest input, Context context) {
        log.info("Got requested with body {}", input.toString());

//        String cardsList = input.getCardsList();
//        List<TrelloCard> trelloCards = objectMapper.readValue(cardsList, new TypeReference<List<TrelloCard>>(){});

        input.getCards().forEach(e -> log.info(e.getDesc()));

        return new APIGatewayProxyResponseEvent()
                .withBody("test")
                .withStatusCode(200);
    }
}
