package com.umbrella.insurance.core.models.gameTypes.athletic.goal.soccer.v1;

import com.umbrella.insurance.core.models.entities.GameType;
import lombok.Data;

@Data
public class Soccer extends GameType {
    static int NUMBER_OF_TEAM_MEMBERS = 11;
    static boolean IS_MIXED_SEX_COMPETITION = false;
    static boolean IS_CONTACT = true;
    static boolean IS_TEAM = true;
    static String TYPE = "ball";
    static String[] equipment = {"soccer ball", "shin pads"};
    static String VENUE = "soccer field";
    static int NUMBER_OF_TEAMS = 2;
    static boolean HAS_COIN_TOSS = true;

}
