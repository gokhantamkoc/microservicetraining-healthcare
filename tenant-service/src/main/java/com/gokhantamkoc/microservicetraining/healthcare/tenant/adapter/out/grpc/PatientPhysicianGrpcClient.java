package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.grpc;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

@Component
public class PatientPhysicianGrpcClient {
    private final DiscoveryClient discoveryClient;
    private final String patientServiceId;
    private final int patientFallbackPort;
    private final String physicianServiceId;
    private final int physicianFallbackPort;

    public PatientPhysicianGrpcClient(
            DiscoveryClient discoveryClient,
            @Value("${grpc.clients.patient-service-id}") String patientServiceId,
            @Value("${grpc.clients.patient-port}") int patientFallbackPort,
            @Value("${grpc.clients.physician-service-id}") String physicianServiceId,
            @Value("${grpc.clients.physician-port}") int physicianFallbackPort) {
        this.discoveryClient = discoveryClient;
        this.patientServiceId = patientServiceId;
        this.patientFallbackPort = patientFallbackPort;
        this.physicianServiceId = physicianServiceId;
        this.physicianFallbackPort = physicianFallbackPort;
    }

    public PatientSummary getPatient(Long patientId) {
        ManagedChannel channel = channel(patientServiceId, patientFallbackPort);
        try {
            PatientReply reply = PatientLookupServiceGrpc.newBlockingStub(channel)
                    .getPatient(PatientRequest.newBuilder().setPatientId(String.valueOf(patientId)).build());
            if (!reply.getActive()) {
                throw new IllegalArgumentException("Patient not found: " + patientId);
            }
            return new PatientSummary(Long.valueOf(reply.getPatientId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getPhoneNumber());
        } finally {
            channel.shutdown();
        }
    }

    public PhysicianSummary getPhysician(Long physicianId) {
        ManagedChannel channel = channel(physicianServiceId, physicianFallbackPort);
        try {
            PhysicianReply reply = PhysicianLookupServiceGrpc.newBlockingStub(channel)
                    .getPhysician(PhysicianRequest.newBuilder().setPhysicianId(String.valueOf(physicianId)).build());
            if (!reply.getActive()) {
                throw new IllegalArgumentException("Physician not found: " + physicianId);
            }
            return new PhysicianSummary(Long.valueOf(reply.getPhysicianId()), Long.valueOf(reply.getTenantId()), reply.getFullName(), reply.getSpecialty());
        } finally {
            channel.shutdown();
        }
    }

    public CompletableFuture<PatientSummary> getPatientAsync(Long patientId) {
        CompletableFuture<PatientSummary> future = new CompletableFuture<>();
        ManagedChannel channel = channel(patientServiceId, patientFallbackPort);
        PatientLookupServiceGrpc.newStub(channel).getPatient(PatientRequest.newBuilder().setPatientId(String.valueOf(patientId)).build(), new StreamObserver<>() {
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
                channel.shutdown();
                future.completeExceptionally(throwable);
            }

            @Override
            public void onCompleted() {
                channel.shutdown();
            }
        });
        return future;
    }

    public CompletableFuture<PhysicianSummary> getPhysicianAsync(Long physicianId) {
        CompletableFuture<PhysicianSummary> future = new CompletableFuture<>();
        ManagedChannel channel = channel(physicianServiceId, physicianFallbackPort);
        PhysicianLookupServiceGrpc.newStub(channel).getPhysician(PhysicianRequest.newBuilder().setPhysicianId(String.valueOf(physicianId)).build(), new StreamObserver<>() {
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
                channel.shutdown();
                future.completeExceptionally(throwable);
            }

            @Override
            public void onCompleted() {
                channel.shutdown();
            }
        });
        return future;
    }

    private ManagedChannel channel(String serviceId, int fallbackPort) {
        ServiceInstance instance = instance(serviceId);
        int port = Integer.parseInt(instance.getMetadata().getOrDefault("grpc-port", String.valueOf(fallbackPort)));
        return ManagedChannelBuilder.forAddress(instance.getHost(), port).usePlaintext().build();
    }

    private ServiceInstance instance(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            throw new IllegalStateException("No service instance found in discovery-server for " + serviceId);
        }
        return instances.getFirst();
    }

    public record PatientSummary(Long id, Long tenantId, String fullName, String phoneNumber) {
    }

    public record PhysicianSummary(Long id, Long tenantId, String fullName, String specialty) {
    }
}

