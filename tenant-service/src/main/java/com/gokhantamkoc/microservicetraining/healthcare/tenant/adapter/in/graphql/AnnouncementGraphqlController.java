package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.AnnouncementUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;

@Controller
class AnnouncementGraphqlController {
    private final AnnouncementUseCase announcementUseCase;

    AnnouncementGraphqlController(AnnouncementUseCase announcementUseCase) {
        this.announcementUseCase = announcementUseCase;
    }

    @QueryMapping
    List<Announcement> announcements(@Argument Long tenantId) {
        return announcementUseCase.listAnnouncements(tenantId);
    }

    @MutationMapping
    Announcement createAnnouncement(@Argument Long tenantId, @Argument String title, @Argument String body) {
        return announcementUseCase.createAnnouncement(tenantId, title, body);
    }
}

