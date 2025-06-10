package com.umbrella.insurance.core.models.gameTypes.v1;

public enum GameTypeEnum {
    Baseball,
    Basketball,
    Cricket,
    Football,
    Golf,
    Hockey,
    Rugby,
    Soccer,
    Table_Tennis,
    Tennis,
    Volleyball;
    public String toString() {
        return this.name().replace("_", " ");
    }
}
