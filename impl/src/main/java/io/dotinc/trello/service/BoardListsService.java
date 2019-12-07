package io.dotinc.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotinc.trello.config.TrelloConfig;
import io.dotinc.core.ResponseWrapper;
import io.dotinc.trello.response.GatewayResponse;
import io.dotinc.trello.model.TrelloBoard;
import io.dotinc.trello.model.TrelloCard;
import io.dotinc.trello.model.TrelloList;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Log4j2
@Singleton
public class BoardListsService {

    @Inject
    TrelloConfig trelloConfig;

    @Inject
    @RestClient
    BoardServiceRestClient boardServiceRestClient;

    @Inject
    @RestClient
    ListServiceRestClient listServiceRestClient;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<TrelloList> convertListByBoardResponse(ResponseWrapper<List<TrelloList>> response) throws IOException {
        String jsonResponse = response.getResponse().readEntity(String.class);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<TrelloList>>(){});
    }

    public TrelloBoard convertBoardByIdResponse(ResponseWrapper<TrelloBoard> response) throws IOException {
        String jsonResponse = response.getResponse().readEntity(String.class);
        return objectMapper.readValue(jsonResponse, TrelloBoard.class);
    }

    public TrelloList convertSpecificListByBoardAndList(ResponseWrapper<TrelloList> response) throws IOException {
        String jsonResponse = response.getResponse().readEntity(String.class);
        return objectMapper.readValue(jsonResponse, TrelloList.class);
    }

    public List<TrelloCard> convertCardsByList(ResponseWrapper<List<TrelloCard>> response) throws IOException {
        String jsonResponse = response.getResponse().readEntity(String.class);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<TrelloCard>>(){});
    }

    public GatewayResponse buildGatewayResponse(ResponseWrapper<List<TrelloList>> trelloResponse, List<TrelloList> trelloList) throws JsonProcessingException {
        String trelloListJson = objectMapper.writeValueAsString(trelloList);
        return buildGatewayResponseInternally(trelloResponse.getResponse(), trelloListJson);
    }

    public GatewayResponse buildGatewayResponse(ResponseWrapper<TrelloBoard> trelloResponse, TrelloBoard trelloBoard) throws JsonProcessingException {
        String trelloBoardJson = objectMapper.writeValueAsString(trelloBoard);
        return buildGatewayResponseInternally(trelloResponse.getResponse(), trelloBoardJson);
    }

    public GatewayResponse buildGatewayResponse(ResponseWrapper<TrelloList> trelloResponse, TrelloList trelloList) throws JsonProcessingException {
        String trelloListJson = objectMapper.writeValueAsString(trelloList);
        return buildGatewayResponseInternally(trelloResponse.getResponse(), trelloListJson);
    }

    public GatewayResponse buildGatewayResponseForTrelloCards(ResponseWrapper<List<TrelloCard>> trelloResponse, List<TrelloCard> trelloList) throws JsonProcessingException {
        String trelloListJson = objectMapper.writeValueAsString(trelloList);
        return buildGatewayResponseInternally(trelloResponse.getResponse(), trelloListJson);
    }

    private GatewayResponse buildGatewayResponseInternally(Response trelloResponse, String bodyJson) {
        Map<String, String> responseHeaders = trelloResponse.getStringHeaders().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
        return GatewayResponse.builder().body(bodyJson).statusCode(trelloResponse.getStatus()).headers(responseHeaders).build();
    }

    public ResponseWrapper<TrelloBoard> getBoardById(String boardId) {
        return new ResponseWrapper<>(boardServiceRestClient.getBoardById(boardId, trelloConfig.getKey(), trelloConfig.getToken()));
    }

    public ResponseWrapper<List<TrelloList>> getListsByBoard(String boardId) {
        return new ResponseWrapper<>(boardServiceRestClient.getListsByBoard(boardId, trelloConfig.getKey(), trelloConfig.getToken()));
    }

    public ResponseWrapper<TrelloList> getSpecificListByBoardAndList(String boardId, @NotNull String listName) throws IOException {
        Optional<String> maybeTrelloListId = convertListByBoardResponse(getListsByBoard(boardId)).stream().filter(e -> e.getName().equals(listName)).map(TrelloList::getId).findFirst();
        String trelloListId = maybeTrelloListId.orElse("");
        log.info("Got list id " + trelloListId);

        return new ResponseWrapper<>(listServiceRestClient.getSpecificListByBoardAndList(trelloListId, trelloConfig.getKey(), trelloConfig.getToken()));
    }

    public ResponseWrapper<List<TrelloCard>> getCardsByList(String listId) {
        log.info("Got list id {}", listId);
        return new ResponseWrapper<>(listServiceRestClient.getCardsByList(listId, trelloConfig.getKey(), trelloConfig.getToken()));
    }
}
