package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.notification.application.NotificationUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationOperationStatus;

@Controller
class NotificationGraphqlController {
    private final NotificationUseCase notificationUseCase;

    NotificationGraphqlController(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @QueryMapping
    List<NotificationDelivery> notifications(@Argument Long tenantId) {
        return notificationUseCase.listByTenant(tenantId);
    }

    @org.springframework.graphql.data.method.annotation.MutationMapping
    NotificationDelivery sendAppointmentNotification(@Argument Long tenantId, @Argument Long patientId, @Argument Long physicianId) {
        return notificationUseCase.deliverAppointmentNotification(tenantId, patientId, physicianId);
    }

    @org.springframework.graphql.data.method.annotation.MutationMapping
    NotificationOperationStatus sendAppointmentNotificationAsync(@Argument Long tenantId, @Argument Long patientId, @Argument Long physicianId) {
        return notificationUseCase.startAppointmentNotificationAsync(tenantId, patientId, physicianId);
    }

    @QueryMapping
    NotificationOperationStatus appointmentNotificationStatus(@Argument String operationId) {
        return notificationUseCase.appointmentNotificationStatus(operationId);
    }
}
