package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.grpc;

import org.springframework.stereotype.Component;

@Component
public class PatientValidationClient {
    public boolean patientExists(Long patientId) {
        return patientId != null && patientId > 0;
    }
}

