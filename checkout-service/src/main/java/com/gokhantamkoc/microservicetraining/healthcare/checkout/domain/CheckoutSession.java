package com.gokhantamkoc.microservicetraining.healthcare.checkout.domain;

import java.math.BigDecimal;

public record CheckoutSession(Long id, Long tenantId, Long patientId, BigDecimal amount, CheckoutStatus status) {
}

