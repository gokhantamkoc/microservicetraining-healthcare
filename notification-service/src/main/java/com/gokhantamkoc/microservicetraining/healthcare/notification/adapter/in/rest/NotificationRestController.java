package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.notification.application.NotificationUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationOperationStatus;

@RestController
class NotificationRestController {
    private final NotificationUseCase notificationUseCase;

    NotificationRestController(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @GetMapping("/notifications")
    List<NotificationDelivery> list(@RequestParam Long tenantId) {
        return notificationUseCase.listByTenant(tenantId);
    }

    @PostMapping("/notifications/appointment/sync")
    NotificationDelivery appointmentSync(@RequestBody AppointmentNotificationRequest request) {
        return notificationUseCase.deliverAppointmentNotification(request.tenantId(), request.patientId(), request.physicianId());
    }

    @PostMapping("/notifications/appointment/async")
    ResponseEntity<NotificationOperationStatus> appointmentAsync(@RequestBody AppointmentNotificationRequest request) {
        return ResponseEntity.accepted().body(notificationUseCase.startAppointmentNotificationAsync(
                request.tenantId(), request.patientId(), request.physicianId()));
    }

    @GetMapping("/notifications/appointment/async/{operationId}")
    NotificationOperationStatus appointmentAsyncStatus(@PathVariable String operationId) {
        return notificationUseCase.appointmentNotificationStatus(operationId);
    }

    record AppointmentNotificationRequest(Long tenantId, Long patientId, Long physicianId) {
    }
}
