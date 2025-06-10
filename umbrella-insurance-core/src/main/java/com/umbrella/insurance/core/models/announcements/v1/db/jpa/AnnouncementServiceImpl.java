package com.umbrella.insurance.core.models.announcements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    AnnouncementRepository announcementRepository;

    @Override
    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    @Override
    public Optional<Announcement> getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId);
    }

    @Override
    public List<Announcement> getAnnouncementByIds(List<Long> announcements) {
        return announcementRepository.findAllById(announcements);
    }

    @Override
    public Optional<Announcement> getAnnouncementByName(String announcementName) {
        return announcementRepository.findAnnouncementByAnnouncementName(announcementName);
    }
}
