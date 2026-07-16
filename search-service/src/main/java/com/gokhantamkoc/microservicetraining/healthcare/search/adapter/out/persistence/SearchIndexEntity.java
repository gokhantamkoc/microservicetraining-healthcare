package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.out.persistence;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_history_index")
class SearchIndexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long tenantId;
    Long patientId;
    String summary;
    Instant occurredAt;
}

