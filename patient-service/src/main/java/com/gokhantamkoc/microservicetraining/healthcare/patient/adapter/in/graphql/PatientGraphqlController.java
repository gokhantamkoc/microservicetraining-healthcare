package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.patient.application.PatientUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

@Controller
class PatientGraphqlController {
    private final PatientUseCase patientUseCase;

    PatientGraphqlController(PatientUseCase patientUseCase) {
        this.patientUseCase = patientUseCase;
    }

    @QueryMapping
    List<Patient> patients(@Argument Long tenantId) {
        return patientUseCase.listByTenant(tenantId);
    }

    @MutationMapping
    Patient createPatient(@Argument Long tenantId, @Argument String fullName, @Argument String medicalRecordNumber, @Argument String phoneNumber) {
        return patientUseCase.create(tenantId, fullName, medicalRecordNumber, phoneNumber);
    }
}

