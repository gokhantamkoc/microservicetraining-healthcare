package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.physician.application.PhysicianUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

@RestController
class PhysicianRestController {
    private final PhysicianUseCase physicianUseCase;

    PhysicianRestController(PhysicianUseCase physicianUseCase) {
        this.physicianUseCase = physicianUseCase;
    }

    @PostMapping("/physicians")
    Physician create(@RequestBody CreatePhysicianRequest request) {
        return physicianUseCase.create(request.tenantId(), request.fullName(), request.specialty());
    }

    @GetMapping("/physicians")
    List<Physician> list(@RequestParam Long tenantId) {
        return physicianUseCase.listByTenant(tenantId);
    }

    record CreatePhysicianRequest(Long tenantId, String fullName, String specialty) {
    }
}

