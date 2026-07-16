package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.rest;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.AppointmentUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.AppointmentOperationStatus;

@RestController
class AppointmentRestController {
    private final AppointmentUseCase appointmentUseCase;

    AppointmentRestController(AppointmentUseCase appointmentUseCase) {
        this.appointmentUseCase = appointmentUseCase;
    }

    @PostMapping("/appointments")
    Appointment create(@RequestBody CreateAppointmentRequest request) {
        return createSync(request);
    }

    @PostMapping("/appointments/sync")
    Appointment createSync(@RequestBody CreateAppointmentRequest request) {
        return appointmentUseCase.createAppointment(request.tenantId(), request.patientId(), request.physicianId(), request.startsAt());
    }

    @PostMapping("/appointments/async")
    ResponseEntity<AppointmentOperationStatus> createAsync(@RequestBody CreateAppointmentRequest request) {
        return ResponseEntity.accepted().body(appointmentUseCase.startCreateAppointmentAsync(
                request.tenantId(), request.patientId(), request.physicianId(), request.startsAt()));
    }

    @GetMapping("/appointments/async/{operationId}")
    AppointmentOperationStatus asyncStatus(@PathVariable String operationId) {
        return appointmentUseCase.asyncStatus(operationId);
    }

    @GetMapping("/appointments")
    List<Appointment> list(@RequestParam Long tenantId) {
        return appointmentUseCase.listAppointments(tenantId);
    }

    record CreateAppointmentRequest(Long tenantId, Long patientId, Long physicianId, Instant startsAt) {
    }
}
