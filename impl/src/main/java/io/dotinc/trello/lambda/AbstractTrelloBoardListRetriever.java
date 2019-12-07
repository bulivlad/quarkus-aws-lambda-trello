package io.dotinc.trello.lambda;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dotinc.trello.service.BoardListsService;
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
    BoardListsService boardListsService;

    ObjectMapper objectMapper = new ObjectMapper();
}
