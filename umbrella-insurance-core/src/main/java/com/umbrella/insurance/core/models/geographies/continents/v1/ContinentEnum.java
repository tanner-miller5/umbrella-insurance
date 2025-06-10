package com.umbrella.insurance.core.models.geographies.continents.v1;

public enum ContinentEnum {
    North_America,
    Asia,
    Africa,
    Europe,
    South_America,
    Antarctica,
    Oceania;
    public String toString() {
        return this.name().replace("_", " ");
    }
}
