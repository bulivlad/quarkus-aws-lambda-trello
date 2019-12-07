package io.dotinc.trello.lambda;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotinc.trello.service.BoardService;
import io.dotinc.trello.service.ListService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Singleton
@FieldDefaults(level = AccessLevel.PROTECTED)
public class AbstractTrelloBoardListRetriever {
    @Inject
    BoardService boardService;

    @Inject
    ListService listService;

    ObjectMapper objectMapper = new ObjectMapper();
}
