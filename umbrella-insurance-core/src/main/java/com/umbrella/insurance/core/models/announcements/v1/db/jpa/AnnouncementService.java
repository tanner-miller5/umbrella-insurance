package com.umbrella.insurance.core.models.announcements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    Announcement saveAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncements();
    Announcement updateAnnouncement(Announcement announcement);
    void deleteAnnouncement(Long announcementId);
    Optional<Announcement> getAnnouncementById(Long announcementId);
    List<Announcement> getAnnouncementByIds(List<Long> announcements);
    Optional<Announcement> getAnnouncementByName(String announcementName);
}
