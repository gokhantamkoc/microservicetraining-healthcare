package com.gokhantamkoc.microservicetraining.healthcare.notification.application;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.grpc.PatientPhysicianGrpcClient;
import com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.persistence.NotificationPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationOperationStatus;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationProvider;

@Service
@Transactional
public class NotificationService implements NotificationUseCase {
    private final NotificationPersistenceAdapter persistence;
    private final PatientPhysicianGrpcClient grpcClient;
    private final Map<String, NotificationOperationStatus> asyncOperations = new ConcurrentHashMap<>();

    public NotificationService(NotificationPersistenceAdapter persistence, PatientPhysicianGrpcClient grpcClient) {
        this.persistence = persistence;
        this.grpcClient = grpcClient;
    }

    @Override
    public NotificationProvider providerForTenant(Long tenantId) {
        int index = Math.toIntExact(Math.floorMod(tenantId == null ? 0 : tenantId, 3));
        return NotificationProvider.values()[index];
    }

    @Override
    public NotificationDelivery deliver(Long tenantId, Long patientId, NotificationProvider provider, String message) {
        return persistence.save(new NotificationDelivery(null, tenantId, patientId, null, provider, message, Instant.now()));
    }

    @Override
    public NotificationDelivery deliverAppointmentNotification(Long tenantId, Long patientId, Long physicianId) {
        PatientPhysicianGrpcClient.PatientSummary patient = grpcClient.getPatient(patientId);
        PatientPhysicianGrpcClient.PhysicianSummary physician = grpcClient.getPhysician(physicianId);
        return saveAppointmentNotification(tenantId, patientId, physicianId, patient, physician);
    }

    @Override
    public NotificationOperationStatus startAppointmentNotificationAsync(Long tenantId, Long patientId, Long physicianId) {
        String operationId = UUID.randomUUID().toString();
        asyncOperations.put(operationId, new NotificationOperationStatus(operationId, "RUNNING", null, null));
        CompletableFuture<PatientPhysicianGrpcClient.PatientSummary> patientFuture = grpcClient.getPatientAsync(patientId);
        CompletableFuture<PatientPhysicianGrpcClient.PhysicianSummary> physicianFuture = grpcClient.getPhysicianAsync(physicianId);
        patientFuture.thenCombine(physicianFuture, (patient, physician) -> saveAppointmentNotification(tenantId, patientId, physicianId, patient, physician))
                .whenComplete((delivery, throwable) -> {
                    if (throwable != null) {
                        asyncOperations.put(operationId, new NotificationOperationStatus(operationId, "FAILED", null, throwable.getMessage()));
                    } else {
                        asyncOperations.put(operationId, new NotificationOperationStatus(operationId, "COMPLETED", delivery, null));
                    }
                });
        return asyncOperations.get(operationId);
    }

    @Override
    public NotificationOperationStatus appointmentNotificationStatus(String operationId) {
        return asyncOperations.getOrDefault(operationId, new NotificationOperationStatus(operationId, "NOT_FOUND", null, "Unknown operation id"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDelivery> listByTenant(Long tenantId) {
        return persistence.findByTenantId(tenantId);
    }

    private NotificationDelivery saveAppointmentNotification(
            Long tenantId,
            Long patientId,
            Long physicianId,
            PatientPhysicianGrpcClient.PatientSummary patient,
            PatientPhysicianGrpcClient.PhysicianSummary physician) {
        NotificationProvider provider = providerForTenant(tenantId);
        String message = "Appointment scheduled for " + patient.fullName()
                + " with " + physician.fullName()
                + " (" + physician.specialty() + ")";
        return persistence.save(new NotificationDelivery(null, tenantId, patientId, physicianId, provider, message, Instant.now()));
    }
}
