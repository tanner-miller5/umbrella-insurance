package com.umbrella.insurance.core.models.announcements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query(value = "SELECT * FROM announcements a WHERE a.announcement_name = :announcementName", nativeQuery = true)
    Optional<Announcement> findAnnouncementByAnnouncementName(String announcementName);
}
