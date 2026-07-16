package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.graphql;

import java.time.Instant;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.AppointmentUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.AppointmentOperationStatus;

@Controller
class AppointmentGraphqlController {
    private final AppointmentUseCase appointmentUseCase;

    AppointmentGraphqlController(AppointmentUseCase appointmentUseCase) {
        this.appointmentUseCase = appointmentUseCase;
    }

    @QueryMapping
    List<Appointment> appointments(@Argument Long tenantId) {
        return appointmentUseCase.listAppointments(tenantId);
    }

    @MutationMapping
    Appointment createAppointment(@Argument Long tenantId, @Argument Long patientId, @Argument Long physicianId, @Argument String startsAt) {
        return appointmentUseCase.createAppointment(tenantId, patientId, physicianId, Instant.parse(startsAt));
    }

    @MutationMapping
    AppointmentOperationStatus createAppointmentAsync(@Argument Long tenantId, @Argument Long patientId, @Argument Long physicianId, @Argument String startsAt) {
        return appointmentUseCase.startCreateAppointmentAsync(tenantId, patientId, physicianId, Instant.parse(startsAt));
    }

    @QueryMapping
    AppointmentOperationStatus appointmentAsyncStatus(@Argument String operationId) {
        return appointmentUseCase.asyncStatus(operationId);
    }
}
