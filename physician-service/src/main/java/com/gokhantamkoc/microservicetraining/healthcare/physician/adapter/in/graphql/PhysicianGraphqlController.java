package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.physician.application.PhysicianUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

@Controller
class PhysicianGraphqlController {
    private final PhysicianUseCase physicianUseCase;

    PhysicianGraphqlController(PhysicianUseCase physicianUseCase) {
        this.physicianUseCase = physicianUseCase;
    }

    @QueryMapping
    List<Physician> physicians(@Argument Long tenantId) {
        return physicianUseCase.listByTenant(tenantId);
    }

    @MutationMapping
    Physician createPhysician(@Argument Long tenantId, @Argument String fullName, @Argument String specialty) {
        return physicianUseCase.create(tenantId, fullName, specialty);
    }
}

