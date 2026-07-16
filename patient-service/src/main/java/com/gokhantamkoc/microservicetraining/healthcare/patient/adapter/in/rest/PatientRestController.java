package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.patient.application.PatientUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

@RestController
class PatientRestController {
    private final PatientUseCase patientUseCase;

    PatientRestController(PatientUseCase patientUseCase) {
        this.patientUseCase = patientUseCase;
    }

    @PostMapping("/patients")
    Patient create(@RequestBody CreatePatientRequest request) {
        return patientUseCase.create(request.tenantId(), request.fullName(), request.medicalRecordNumber(), request.phoneNumber());
    }

    @GetMapping("/patients/{id}")
    ResponseEntity<Patient> find(@PathVariable Long id) {
        return ResponseEntity.of(patientUseCase.findById(id));
    }

    @GetMapping("/patients")
    List<Patient> list(@RequestParam Long tenantId) {
        return patientUseCase.listByTenant(tenantId);
    }

    record CreatePatientRequest(Long tenantId, String fullName, String medicalRecordNumber, String phoneNumber) {
    }
}

