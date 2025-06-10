package com.umbrella.insurance.core.models.users.bots.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Bot;

import java.util.List;
import java.util.Optional;

public interface BotService {
    Bot saveBot(Bot bot);
    List<Bot> getBots();
    Bot updateBot(Bot bot);
    void deleteBot(Long botId);
    Optional<Bot> getBotByBotName(String botName);
    void deleteBotByBotName(String botName);
    Optional<Bot> getBotById(Long botId);
}
