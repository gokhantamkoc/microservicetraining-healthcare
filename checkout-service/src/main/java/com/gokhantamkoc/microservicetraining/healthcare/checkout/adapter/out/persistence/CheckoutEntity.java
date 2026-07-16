package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.persistence;

import java.math.BigDecimal;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout_sessions")
class CheckoutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long tenantId;
    Long patientId;
    BigDecimal amount;
    @Enumerated(EnumType.STRING)
    CheckoutStatus status;
}

