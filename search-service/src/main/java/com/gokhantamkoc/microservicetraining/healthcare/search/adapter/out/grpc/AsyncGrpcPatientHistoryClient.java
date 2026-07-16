package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.out.grpc;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

@Component
public class AsyncGrpcPatientHistoryClient {
    public CompletableFuture<List<String>> streamHistorySummaries(Long patientId) {
        return CompletableFuture.completedFuture(List.of("Async gRPC example for patient " + patientId));
    }
}

