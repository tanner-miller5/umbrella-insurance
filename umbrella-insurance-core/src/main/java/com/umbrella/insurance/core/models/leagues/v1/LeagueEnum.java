package com.umbrella.insurance.core.models.leagues.v1;

public enum LeagueEnum {
    //football
    NFL,
    //soccer
    AFC,
    CAF,
    CONCACAF,
    CONMEBOL,
    OFC,
    UEFA,
    //soccer international
    Olympic,
    World_Cup;
    public String toString() {
        return this.name().replace("_", " ");
    }

}
