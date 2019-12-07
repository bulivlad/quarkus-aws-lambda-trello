package io.dotinc.trello.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author vladclaudiubulimac on 07/12/2019.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrelloList {
    String id;
    String name;
    boolean closed;
    String idBoard;
    Float pos;
    boolean subscribed;
    Integer softLimit;
}
