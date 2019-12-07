package io.dotinc.trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrelloBoardPrefs {
    String permissionLevel;
    String invitations;
    boolean selfJoin;
    boolean canBePublic;
    boolean canBeOrg;
    boolean canBePrivate;
    boolean canInvite;
}
