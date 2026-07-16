package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.util.List;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;

public interface AnnouncementUseCase {
    Announcement createAnnouncement(Long tenantId, String title, String body);
    List<Announcement> listAnnouncements(Long tenantId);
}

