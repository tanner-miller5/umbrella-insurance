package com.umbrella.insurance.core.models.people.referees.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Referee;

import java.util.List;
import java.util.Optional;

public interface RefereeService {
    Referee saveReferee(Referee referee);
    List<Referee> getReferees();
    Referee updateReferee(Referee referee);
    void deleteReferee(Long refereeId);
    Optional<Referee> getReferee(Long refereeId);
    Optional<Referee> getRefereeByPersonId(Long personId);
    void deleteRefereeByPersonId(Long personId);
}
