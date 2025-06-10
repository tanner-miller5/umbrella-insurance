package com.umbrella.insurance.core.models.geographies.states.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.State;
import java.util.List;
import java.util.Optional;

public interface StateService {
    State saveState(State state);
    List<State> getStates();
    State updateState(State state);
    void deleteState(Long stateId);
    Optional<State> getStateByStateName(String stateName);
    Optional<State> getStateById(Long stateId);
    void deleteStateByStateName(String stateName);
}
