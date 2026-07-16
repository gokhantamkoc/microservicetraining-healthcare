package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence.AnnouncementPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;

@Service
@Transactional
public class AnnouncementService implements AnnouncementUseCase {
    private final AnnouncementPersistenceAdapter persistence;

    public AnnouncementService(AnnouncementPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public Announcement createAnnouncement(Long tenantId, String title, String body) {
        return persistence.save(new Announcement(null, tenantId, title, body, Instant.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Announcement> listAnnouncements(Long tenantId) {
        return persistence.findByTenantId(tenantId);
    }
}

