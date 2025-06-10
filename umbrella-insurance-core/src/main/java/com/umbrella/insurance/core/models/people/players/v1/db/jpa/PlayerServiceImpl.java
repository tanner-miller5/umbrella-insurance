package com.umbrella.insurance.core.models.people.players.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Override
    public Optional<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    @Override
    public Optional<Player> getPlayerByPersonId(Long personId) {
        return playerRepository.findByPersonId(personId);
    }

    @Override
    public void deleteByPersonId(Long personId) {
        playerRepository.deleteByPersonId(personId);
    }
}
