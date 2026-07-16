package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.in.grpc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientLookupServiceGrpc;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientReply;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PatientRequest;
import com.gokhantamkoc.microservicetraining.healthcare.patient.application.PatientUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

@Component
class PatientGrpcServer implements SmartLifecycle {
    private final int port;
    private final PatientUseCase patientUseCase;
    private Server server;
    private boolean running;

    PatientGrpcServer(@Value("${grpc.server.port}") int port, PatientUseCase patientUseCase) {
        this.port = port;
        this.patientUseCase = patientUseCase;
    }

    @Override
    public void start() {
        try {
            server = NettyServerBuilder.forPort(port)
                    .addService(new PatientLookupGrpcService(patientUseCase))
                    .build()
                    .start();
            running = true;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not start patient gRPC server", exception);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    private static class PatientLookupGrpcService extends PatientLookupServiceGrpc.PatientLookupServiceImplBase {
        private final PatientUseCase patientUseCase;

        PatientLookupGrpcService(PatientUseCase patientUseCase) {
            this.patientUseCase = patientUseCase;
        }

        @Override
        public void getPatient(PatientRequest request, StreamObserver<PatientReply> responseObserver) {
            PatientReply reply = patientUseCase.findById(Long.valueOf(request.getPatientId()))
                    .map(this::reply)
                    .orElseGet(() -> PatientReply.newBuilder()
                            .setPatientId(request.getPatientId())
                            .setActive(false)
                            .build());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        private PatientReply reply(Patient patient) {
            return PatientReply.newBuilder()
                    .setPatientId(String.valueOf(patient.id()))
                    .setTenantId(String.valueOf(patient.tenantId()))
                    .setFullName(patient.fullName())
                    .setPhoneNumber(patient.phoneNumber())
                    .setActive(true)
                    .build();
        }
    }
}

