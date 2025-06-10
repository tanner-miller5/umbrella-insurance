package com.umbrella.insurance.core.models.users.bots.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BotServiceImpl implements BotService {

    @Autowired
    BotRepository botRepository;

    @Override
    public Bot saveBot(Bot bot) {
        return botRepository.save(bot);
    }

    @Override
    public List<Bot> getBots() {
        return botRepository.findAll();
    }

    @Override
    public Bot updateBot(Bot bot) {
        return botRepository.save(bot);
    }

    @Override
    public void deleteBot(Long botId) {
        botRepository.deleteById(botId);
    }

    @Override
    public Optional<Bot> getBotByBotName(String botName) {
        return botRepository.findBotByBotName(botName);
    }

    @Override
    public void deleteBotByBotName(String botName) {
        botRepository.deleteBotByBotName(botName);
    }

    @Override
    public Optional<Bot> getBotById(Long botId) {
        return botRepository.findById(botId);
    }
}
