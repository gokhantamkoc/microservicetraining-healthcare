package com.gokhantamkoc.microservicetraining.healthcare.checkout.application;

import java.math.BigDecimal;
import java.util.Optional;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutSession;

public interface CheckoutUseCase {
    CheckoutSession startCheckout(Long tenantId, Long patientId, BigDecimal amount);
    Optional<CheckoutSession> status(Long id);
}

