package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.grpc.PatientPhysicianGrpcClient;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence.AppointmentPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.AppointmentOperationStatus;

@Service
@Transactional
public class AppointmentService implements AppointmentUseCase {
    private final AppointmentPersistenceAdapter persistence;
    private final PatientPhysicianGrpcClient grpcClient;
    private final Map<String, AppointmentOperationStatus> asyncOperations = new ConcurrentHashMap<>();

    public AppointmentService(AppointmentPersistenceAdapter persistence, PatientPhysicianGrpcClient grpcClient) {
        this.persistence = persistence;
        this.grpcClient = grpcClient;
    }

    @Override
    public Appointment createAppointment(Long tenantId, Long patientId, Long physicianId, Instant startsAt) {
        PatientPhysicianGrpcClient.PatientSummary patient = grpcClient.getPatient(patientId);
        PatientPhysicianGrpcClient.PhysicianSummary physician = grpcClient.getPhysician(physicianId);
        return saveEnrichedAppointment(tenantId, patientId, physicianId, startsAt, patient, physician);
    }

    @Override
    public AppointmentOperationStatus startCreateAppointmentAsync(Long tenantId, Long patientId, Long physicianId, Instant startsAt) {
        String operationId = UUID.randomUUID().toString();
        asyncOperations.put(operationId, new AppointmentOperationStatus(operationId, "RUNNING", null, null));
        CompletableFuture<PatientPhysicianGrpcClient.PatientSummary> patientFuture = grpcClient.getPatientAsync(patientId);
        CompletableFuture<PatientPhysicianGrpcClient.PhysicianSummary> physicianFuture = grpcClient.getPhysicianAsync(physicianId);
        patientFuture.thenCombine(physicianFuture, (patient, physician) -> saveEnrichedAppointment(tenantId, patientId, physicianId, startsAt, patient, physician))
                .whenComplete((appointment, throwable) -> {
                    if (throwable != null) {
                        asyncOperations.put(operationId, new AppointmentOperationStatus(operationId, "FAILED", null, throwable.getMessage()));
                    } else {
                        asyncOperations.put(operationId, new AppointmentOperationStatus(operationId, "COMPLETED", appointment, null));
                    }
                });
        return asyncOperations.get(operationId);
    }

    @Override
    public AppointmentOperationStatus asyncStatus(String operationId) {
        return asyncOperations.getOrDefault(operationId, new AppointmentOperationStatus(operationId, "NOT_FOUND", null, "Unknown operation id"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> listAppointments(Long tenantId) {
        return persistence.findByTenantId(tenantId);
    }

    private Appointment saveEnrichedAppointment(
            Long tenantId,
            Long patientId,
            Long physicianId,
            Instant startsAt,
            PatientPhysicianGrpcClient.PatientSummary patient,
            PatientPhysicianGrpcClient.PhysicianSummary physician) {
        return persistence.save(new Appointment(null, tenantId, patientId, physicianId,
                patient.fullName(), physician.fullName(), physician.specialty(), startsAt, "SCHEDULED"));
    }
}
