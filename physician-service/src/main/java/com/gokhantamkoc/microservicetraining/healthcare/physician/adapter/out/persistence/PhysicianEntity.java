package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "physicians")
class PhysicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long tenantId;
    String fullName;
    String specialty;
    boolean active;
}

