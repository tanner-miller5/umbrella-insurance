package com.umbrella.insurance.core.models.people.referees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RefereeServiceImpl implements RefereeService {

    @Autowired
    RefereeRepository refereeRepository;

    @Override
    public Referee saveReferee(Referee referee) {
        return refereeRepository.save(referee);
    }

    @Override
    public List<Referee> getReferees() {
        return refereeRepository.findAll();
    }

    @Override
    public Referee updateReferee(Referee referee) {
        return refereeRepository.save(referee);
    }

    @Override
    public void deleteReferee(Long refereeId) {
        refereeRepository.deleteById(refereeId);
    }

    @Override
    public Optional<Referee> getReferee(Long refereeId) {
        return refereeRepository.findById(refereeId);
    }

    @Override
    public Optional<Referee> getRefereeByPersonId(Long personId) {
        return refereeRepository.findByPersonId(personId);
    }

    @Override
    public void deleteRefereeByPersonId(Long personId) {
        refereeRepository.deleteByPersonId(personId);
    }
}
