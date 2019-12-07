package io.dotinc.trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBoard {
    String id;
    String name;
    String desc;
    boolean closed;
    String idOrganization;
    boolean pinned;
    String url;
    @JsonProperty("prefs")
    TrelloBoardPrefs trelloBoardPrefs;
}
