package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.time.Instant;
import java.util.List;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.AppointmentOperationStatus;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;

public interface AppointmentUseCase {
    Appointment createAppointment(Long tenantId, Long patientId, Long physicianId, Instant startsAt);
    AppointmentOperationStatus startCreateAppointmentAsync(Long tenantId, Long patientId, Long physicianId, Instant startsAt);
    AppointmentOperationStatus asyncStatus(String operationId);
    List<Appointment> listAppointments(Long tenantId);
}
