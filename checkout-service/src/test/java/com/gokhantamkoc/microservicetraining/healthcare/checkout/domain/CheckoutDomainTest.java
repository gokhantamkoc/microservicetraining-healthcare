package com.gokhantamkoc.microservicetraining.healthcare.checkout.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CheckoutDomainTest {
    @Test
    void checkoutSessionKeepsPaymentState() {
        CheckoutSession session = new CheckoutSession(1L, 2L, 3L, new BigDecimal("125.50"), CheckoutStatus.STARTED);

        assertThat(session.id()).isEqualTo(1L);
        assertThat(session.tenantId()).isEqualTo(2L);
        assertThat(session.patientId()).isEqualTo(3L);
        assertThat(session.amount()).isEqualByComparingTo("125.50");
        assertThat(session.status()).isEqualTo(CheckoutStatus.STARTED);
    }

    @Test
    void checkoutStatusContainsSagaTerminalStates() {
        assertThat(CheckoutStatus.values()).containsExactly(CheckoutStatus.STARTED, CheckoutStatus.PAID, CheckoutStatus.FAILED);
    }
}

