package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface SearchIndexRepository extends JpaRepository<SearchIndexEntity, Long> {
    List<SearchIndexEntity> findByTenantIdAndSummaryContainingIgnoreCase(Long tenantId, String term);
}

