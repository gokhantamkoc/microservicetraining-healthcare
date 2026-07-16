package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;

@Component
public class AnnouncementPersistenceAdapter {
    private final AnnouncementRepository repository;

    public AnnouncementPersistenceAdapter(AnnouncementRepository repository) {
        this.repository = repository;
    }

    public Announcement save(Announcement announcement) {
        AnnouncementEntity entity = new AnnouncementEntity();
        entity.id = announcement.id() == null ? nextId() : announcement.id();
        entity.tenantId = announcement.tenantId();
        entity.title = announcement.title();
        entity.body = announcement.body();
        entity.publishedAt = announcement.publishedAt();
        return toDomain(repository.save(entity));
    }

    public List<Announcement> findByTenantId(Long tenantId) {
        return repository.findByTenantIdOrderByPublishedAtDesc(tenantId).stream().map(this::toDomain).toList();
    }

    private Announcement toDomain(AnnouncementEntity entity) {
        return new Announcement(entity.id, entity.tenantId, entity.title, entity.body, entity.publishedAt);
    }

    private Long nextId() {
        return repository.findTopByOrderByIdDesc().map(entity -> entity.id + 1).orElse(1L);
    }
}
