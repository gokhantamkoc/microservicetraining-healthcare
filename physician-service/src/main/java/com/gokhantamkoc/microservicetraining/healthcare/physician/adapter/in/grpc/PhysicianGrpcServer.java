package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.in.grpc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianLookupServiceGrpc;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianReply;
import com.gokhantamkoc.microservicetraining.healthcare.grpc.PhysicianRequest;
import com.gokhantamkoc.microservicetraining.healthcare.physician.application.PhysicianUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

@Component
class PhysicianGrpcServer implements SmartLifecycle {
    private final int port;
    private final PhysicianUseCase physicianUseCase;
    private Server server;
    private boolean running;

    PhysicianGrpcServer(@Value("${grpc.server.port}") int port, PhysicianUseCase physicianUseCase) {
        this.port = port;
        this.physicianUseCase = physicianUseCase;
    }

    @Override
    public void start() {
        try {
            server = NettyServerBuilder.forPort(port)
                    .addService(new PhysicianLookupGrpcService(physicianUseCase))
                    .build()
                    .start();
            running = true;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not start physician gRPC server", exception);
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

    private static class PhysicianLookupGrpcService extends PhysicianLookupServiceGrpc.PhysicianLookupServiceImplBase {
        private final PhysicianUseCase physicianUseCase;

        PhysicianLookupGrpcService(PhysicianUseCase physicianUseCase) {
            this.physicianUseCase = physicianUseCase;
        }

        @Override
        public void getPhysician(PhysicianRequest request, StreamObserver<PhysicianReply> responseObserver) {
            PhysicianReply reply = physicianUseCase.findById(Long.valueOf(request.getPhysicianId()))
                    .map(this::reply)
                    .orElseGet(() -> PhysicianReply.newBuilder()
                            .setPhysicianId(request.getPhysicianId())
                            .setActive(false)
                            .build());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        private PhysicianReply reply(Physician physician) {
            return PhysicianReply.newBuilder()
                    .setPhysicianId(String.valueOf(physician.id()))
                    .setTenantId(String.valueOf(physician.tenantId()))
                    .setFullName(physician.fullName())
                    .setSpecialty(physician.specialty())
                    .setActive(physician.active())
                    .build();
        }
    }
}

