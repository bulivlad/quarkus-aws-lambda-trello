package io.dotinc.trello.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author vladclaudiubulimac on 06/12/2019.
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigProperties(prefix = "trello")
public class TrelloConfig {

    String endpoint = "dummy";
    String key;
    String token;

}
