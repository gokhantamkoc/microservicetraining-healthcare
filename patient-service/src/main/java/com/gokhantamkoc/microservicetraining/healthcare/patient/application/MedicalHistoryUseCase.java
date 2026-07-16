package com.gokhantamkoc.microservicetraining.healthcare.patient.application;

import java.util.List;

import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.MedicalHistoryEntry;

public interface MedicalHistoryUseCase {
    MedicalHistoryEntry addHistoryEntry(Long patientId, String summary);
    List<MedicalHistoryEntry> listHistory(Long patientId);
}

