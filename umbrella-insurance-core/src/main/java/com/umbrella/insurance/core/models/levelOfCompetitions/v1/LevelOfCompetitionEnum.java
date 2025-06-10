package com.umbrella.insurance.core.models.levelOfCompetitions.v1;

public enum LevelOfCompetitionEnum {
    College,
    Professional;
    public String toString() {
        return this.name().replace("_", " ");
    }
}
