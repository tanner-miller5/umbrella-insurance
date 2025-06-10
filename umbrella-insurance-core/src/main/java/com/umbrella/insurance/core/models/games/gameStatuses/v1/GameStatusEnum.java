package com.umbrella.insurance.core.models.games.gameStatuses.v1;

public enum GameStatusEnum {
    pending,
    in_progress,
    completed;
    public String toString() {
        return this.name().replace("_", " ");
    }
}
