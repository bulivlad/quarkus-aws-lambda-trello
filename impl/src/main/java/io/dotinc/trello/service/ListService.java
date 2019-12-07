package io.dotinc.trello.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotinc.core.ResponseWrapper;
import io.dotinc.trello.config.TrelloConfig;
import io.dotinc.trello.model.TrelloCard;
import io.dotinc.trello.model.TrelloList;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author vladclaudiubulimac on 08/12/2019.
 */

@Log4j2
@Singleton
public class ListService {

    @Inject
    TrelloConfig trelloConfig;

    @Inject
    BoardService boardService;

    @Inject
    @RestClient
    ListServiceRestClient listServiceRestClient;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<TrelloList> convertListByBoardResponse(ResponseWrapper<List<TrelloList>> response) throws IOException {
        String jsonResponse = response.getResponse().readEntity(String.class);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<TrelloList>>(){});
    }

    public ResponseWrapper<TrelloList> getSpecificListByBoardAndList(String boardId, @NotNull String listName) throws IOException {
        Optional<String> maybeTrelloListId = convertListByBoardResponse(boardService.getListsByBoard(boardId)).stream().filter(e -> e.getName().equals(listName)).map(TrelloList::getId).findFirst();
        String trelloListId = maybeTrelloListId.orElse("");
        log.info("Got list id " + trelloListId);

        return new ResponseWrapper<>(listServiceRestClient.getSpecificListByBoardAndList(trelloListId, trelloConfig.getKey(), trelloConfig.getToken()));
    }

    public ResponseWrapper<List<TrelloCard>> getCardsByList(String listId) {
        log.info("Got list id {}", listId);
        return new ResponseWrapper<>(listServiceRestClient.getCardsByList(listId, trelloConfig.getKey(), trelloConfig.getToken()));
    }
}
