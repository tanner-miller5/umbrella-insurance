package com.umbrella.insurance.core.models.users.sessions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Session;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    public Optional<Session> getSessionBySessionCode(String sessionCode) {
        return sessionRepository.findBySessionCode(sessionCode);
    }

    @Override
    public Optional<Session> getSessionByUserId(Long userId) {
        return sessionRepository.findByUserId(userId);
    }

    @Override
    public void deleteSessionBySessionCode(String sessionCode) {
        sessionRepository.deleteBySessionCode(sessionCode);
    }

    @Override
    public Optional<Session> getSessionBySessionId(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public void deleteSessionByUserId(Long userId) {
        sessionRepository.deleteByUserId(userId);
    }
}
