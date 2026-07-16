package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.grpc;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientLookupServiceGrpc;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientReply;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientRequest;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianLookupServiceGrpc;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianReply;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PreDestroy;

@Component
public class PatientPhysicianGrpcClient {
    private final ManagedChannel patientChannel;
    private final ManagedChannel physicianChannel;
    private final PatientLookupServiceGrpc.PatientLookupServiceBlockingStub patientBlockingStub;
    private final PhysicianLookupServiceGrpc.PhysicianLookupServiceBlockingStub physicianBlockingStub;
    private final PatientLookupServiceGrpc.PatientLookupServiceStub patientAsyncStub;
    private final PhysicianLookupServiceGrpc.PhysicianLookupServiceStub physicianAsyncStub;

    public PatientPhysicianGrpcClient(
            @Value("${grpc.clients.patient-target}") String patientTarget,
            @Value("${grpc.clients.physician-target}") String physicianTarget) {
        this.patientChannel = ManagedChannelBuilder.forTarget(patientTarget).usePlaintext().build();
        this.physicianChannel = ManagedChannelBuilder.forTarget(physicianTarget).usePlaintext().build();
        this.patientBlockingStub = PatientLookupServiceGrpc.newBlockingStub(patientChannel);
        this.physicianBlockingStub = PhysicianLookupServiceGrpc.newBlockingStub(physicianChannel);
        this.patientAsyncStub = PatientLookupServiceGrpc.newStub(patientChannel);
        this.physicianAsyncStub = PhysicianLookupServiceGrpc.newStub(physicianChannel);
    }

    public PatientSummary getPatient(Long patientId) {
        PatientReply reply = patientBlockingStub.getPatient(PatientRequest.newBuilder().setPatientId(String.valueOf(patientId)).build());
        if (!reply.getActive()) {
            throw new IllegalArgumentException("Patient not found: " + patientId);
        }
        return new PatientSummary(Long.valueOf(reply.getPatientId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getPhoneNumber());
    }

    public PhysicianSummary getPhysician(Long physicianId) {
        PhysicianReply reply = physicianBlockingStub.getPhysician(PhysicianRequest.newBuilder().setPhysicianId(String.valueOf(physicianId)).build());
        if (!reply.getActive()) {
            throw new IllegalArgumentException("Physician not found: " + physicianId);
        }
        return new PhysicianSummary(Long.valueOf(reply.getPhysicianId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getSpecialty());
    }

    public CompletableFuture<PatientSummary> getPatientAsync(Long patientId) {
        CompletableFuture<PatientSummary> future = new CompletableFuture<>();
        patientAsyncStub.getPatient(PatientRequest.newBuilder().setPatientId(String.valueOf(patientId)).build(), new StreamObserver<>() {
            @Override
            public void onNext(PatientReply reply) {
                if (!reply.getActive()) {
                    future.completeExceptionally(new IllegalArgumentException("Patient not found: " + patientId));
                    return;
                }
                future.complete(new PatientSummary(Long.valueOf(reply.getPatientId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getPhoneNumber()));
            }

            @Override
            public void onError(Throwable throwable) {
                future.completeExceptionally(throwable);
            }

            @Override
            public void onCompleted() {
            }
        });
        return future;
    }

    public CompletableFuture<PhysicianSummary> getPhysicianAsync(Long physicianId) {
        CompletableFuture<PhysicianSummary> future = new CompletableFuture<>();
        physicianAsyncStub.getPhysician(PhysicianRequest.newBuilder().setPhysicianId(String.valueOf(physicianId)).build(), new StreamObserver<>() {
            @Override
            public void onNext(PhysicianReply reply) {
                if (!reply.getActive()) {
                    future.completeExceptionally(new IllegalArgumentException("Physician not found: " + physicianId));
                    return;
                }
                future.complete(new PhysicianSummary(Long.valueOf(reply.getPhysicianId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getSpecialty()));
            }

            @Override
            public void onError(Throwable throwable) {
                future.completeExceptionally(throwable);
            }

            @Override
            public void onCompleted() {
            }
        });
        return future;
    }

    @PreDestroy
    void shutdown() {
        patientChannel.shutdown();
        physicianChannel.shutdown();
    }

    public record PatientSummary(Long id, Long tenantId, String fullName, String phoneNumber) {
    }

    public record PhysicianSummary(Long id, Long tenantId, String fullName, String specialty) {
    }
}

