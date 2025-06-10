package com.umbrella.insurance.core.models.people.players.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player savePlayer(Player player);
    List<Player> getPlayers();
    Player updatePlayer(Player player);
    void deletePlayer(Long playerId);
    Optional<Player> getPlayerById(Long playerId);
    Optional<Player> getPlayerByPersonId(Long personId);
    void deleteByPersonId(Long personId);
}
