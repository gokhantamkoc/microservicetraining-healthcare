package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.AnnouncementUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;

@RestController
class AnnouncementRestController {
    private final AnnouncementUseCase announcementUseCase;

    AnnouncementRestController(AnnouncementUseCase announcementUseCase) {
        this.announcementUseCase = announcementUseCase;
    }

    @PostMapping("/announcements")
    Announcement create(@RequestBody CreateAnnouncementRequest request) {
        return announcementUseCase.createAnnouncement(request.tenantId(), request.title(), request.body());
    }

    @GetMapping("/announcements")
    List<Announcement> list(@RequestParam Long tenantId) {
        return announcementUseCase.listAnnouncements(tenantId);
    }

    record CreateAnnouncementRequest(Long tenantId, String title, String body) {
    }
}

