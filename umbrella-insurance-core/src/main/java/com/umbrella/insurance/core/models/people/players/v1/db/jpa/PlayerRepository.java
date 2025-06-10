package com.umbrella.insurance.core.models.people.players.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByPersonId(Long personId);
    void deleteByPersonId(Long personId);
}
