package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.patient.application.MedicalHistoryUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.MedicalHistoryEntry;

@RestController
class MedicalHistoryRestController {
    private final MedicalHistoryUseCase medicalHistoryUseCase;

    MedicalHistoryRestController(MedicalHistoryUseCase medicalHistoryUseCase) {
        this.medicalHistoryUseCase = medicalHistoryUseCase;
    }

    @PostMapping("/patients/{patientId}/history")
    MedicalHistoryEntry add(@PathVariable Long patientId, @RequestBody AddHistoryRequest request) {
        return medicalHistoryUseCase.addHistoryEntry(patientId, request.summary());
    }

    @GetMapping("/patients/{patientId}/history")
    List<MedicalHistoryEntry> list(@PathVariable Long patientId) {
        return medicalHistoryUseCase.listHistory(patientId);
    }

    record AddHistoryRequest(String summary) {
    }
}

