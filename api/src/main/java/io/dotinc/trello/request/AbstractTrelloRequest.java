package io.dotinc.trello.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * @author vladclaudiubulimac on 06/12/2019.
 */

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractTrelloRequest {
    int statusCode;
    Map<String, String> headers;
}
