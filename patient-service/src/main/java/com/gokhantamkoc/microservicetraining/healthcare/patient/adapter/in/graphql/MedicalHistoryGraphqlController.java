package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.patient.application.MedicalHistoryUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.MedicalHistoryEntry;

@Controller
class MedicalHistoryGraphqlController {
    private final MedicalHistoryUseCase medicalHistoryUseCase;

    MedicalHistoryGraphqlController(MedicalHistoryUseCase medicalHistoryUseCase) {
        this.medicalHistoryUseCase = medicalHistoryUseCase;
    }

    @QueryMapping
    List<MedicalHistoryEntry> medicalHistory(@Argument Long patientId) {
        return medicalHistoryUseCase.listHistory(patientId);
    }

    @MutationMapping
    MedicalHistoryEntry addMedicalHistoryEntry(@Argument Long patientId, @Argument String summary) {
        return medicalHistoryUseCase.addHistoryEntry(patientId, summary);
    }
}

