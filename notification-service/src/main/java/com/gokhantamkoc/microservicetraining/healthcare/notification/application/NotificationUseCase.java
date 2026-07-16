package com.gokhantamkoc.microservicetraining.healthcare.notification.application;

import java.util.List;

import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationOperationStatus;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationProvider;

public interface NotificationUseCase {
    NotificationProvider providerForTenant(Long tenantId);
    NotificationDelivery deliver(Long tenantId, Long patientId, NotificationProvider provider, String message);
    NotificationDelivery deliverAppointmentNotification(Long tenantId, Long patientId, Long physicianId);
    NotificationOperationStatus startAppointmentNotificationAsync(Long tenantId, Long patientId, Long physicianId);
    NotificationOperationStatus appointmentNotificationStatus(String operationId);
    List<NotificationDelivery> listByTenant(Long tenantId);
}
