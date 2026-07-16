package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.search.domain.PatientHistoryIndex;

@Component
public class SearchPersistenceAdapter {
    private final SearchIndexRepository repository;

    public SearchPersistenceAdapter(SearchIndexRepository repository) {
        this.repository = repository;
    }

    public PatientHistoryIndex save(PatientHistoryIndex index) {
        SearchIndexEntity entity = new SearchIndexEntity();
        entity.tenantId = index.tenantId();
        entity.patientId = index.patientId();
        entity.summary = index.summary();
        entity.occurredAt = index.occurredAt();
        return toDomain(repository.save(entity));
    }

    public List<PatientHistoryIndex> search(Long tenantId, String term) {
        return repository.findByTenantIdAndSummaryContainingIgnoreCase(tenantId, term).stream().map(this::toDomain).toList();
    }

    private PatientHistoryIndex toDomain(SearchIndexEntity entity) {
        return new PatientHistoryIndex(entity.id, entity.tenantId, entity.patientId, entity.summary, entity.occurredAt);
    }
}

