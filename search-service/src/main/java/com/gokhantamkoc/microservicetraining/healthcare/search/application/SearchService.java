package com.gokhantamkoc.microservicetraining.healthcare.search.application;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.search.adapter.out.persistence.SearchPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.search.domain.PatientHistoryIndex;

@Service
@Transactional
public class SearchService implements SearchUseCase {
    private final SearchPersistenceAdapter persistence;

    public SearchService(SearchPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public PatientHistoryIndex index(Long tenantId, Long patientId, String summary) {
        return persistence.save(new PatientHistoryIndex(null, tenantId, patientId, summary, Instant.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientHistoryIndex> search(Long tenantId, String term) {
        return persistence.search(tenantId, term);
    }
}

