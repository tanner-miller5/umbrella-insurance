package com.umbrella.insurance.core.models.users.bots.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BotRepository extends JpaRepository<Bot, Long> {
    Optional<Bot> findBotByBotName(String botName);
    void deleteBotByBotName(String botName);
}
