package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
    List<MedicalHistoryEntity> findByPatientIdOrderByOccurredAtDesc(Long patientId);
}

