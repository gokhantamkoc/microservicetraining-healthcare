package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;

@Component
public class AppointmentPersistenceAdapter {
    private final AppointmentRepository repository;

    public AppointmentPersistenceAdapter(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.id = appointment.id() == null ? nextId() : appointment.id();
        entity.tenantId = appointment.tenantId();
        entity.patientId = appointment.patientId();
        entity.physicianId = appointment.physicianId();
        entity.patientName = appointment.patientName();
        entity.physicianName = appointment.physicianName();
        entity.physicianSpecialty = appointment.physicianSpecialty();
        entity.startsAt = appointment.startsAt();
        entity.status = appointment.status();
        return toDomain(repository.save(entity));
    }

    public List<Appointment> findByTenantId(Long tenantId) {
        return repository.findByTenantIdOrderByStartsAtAsc(tenantId).stream().map(this::toDomain).toList();
    }

    private Appointment toDomain(AppointmentEntity entity) {
        return new Appointment(entity.id, entity.tenantId, entity.patientId, entity.physicianId,
                entity.patientName, entity.physicianName, entity.physicianSpecialty, entity.startsAt, entity.status);
    }

    private Long nextId() {
        return repository.findTopByOrderByIdDesc().map(entity -> entity.id + 1).orElse(1L);
    }
}
