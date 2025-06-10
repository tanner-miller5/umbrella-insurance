package com.umbrella.insurance.core.models.geographies.states.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StateServiceImpl implements StateService {
    @Autowired
    StateRepository stateRepository;

    @Override
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public List<State> getStates() {
        return stateRepository.findAll();
    }

    @Override
    public State updateState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public void deleteState(Long stateId) {
        stateRepository.deleteById(stateId);
    }

    @Override
    public Optional<State> getStateByStateName(String stateName) {
        return stateRepository.findByStateName(stateName);
    }

    @Override
    public Optional<State> getStateById(Long stateId) {
        return stateRepository.findById(stateId);
    }

    @Override
    public void deleteStateByStateName(String stateName) {
        stateRepository.deleteByStateName(stateName);
    }
}
