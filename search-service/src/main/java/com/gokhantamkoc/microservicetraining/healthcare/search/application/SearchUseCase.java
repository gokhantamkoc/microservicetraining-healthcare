package com.gokhantamkoc.microservicetraining.healthcare.search.application;

import java.util.List;

import com.gokhantamkoc.microservicetraining.healthcare.search.domain.PatientHistoryIndex;

public interface SearchUseCase {
    PatientHistoryIndex index(Long tenantId, Long patientId, String summary);
    List<PatientHistoryIndex> search(Long tenantId, String term);
}

