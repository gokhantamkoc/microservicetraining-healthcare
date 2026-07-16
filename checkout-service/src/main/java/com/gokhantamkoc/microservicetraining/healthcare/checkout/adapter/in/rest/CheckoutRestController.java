package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.in.rest;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.application.CheckoutUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutSession;

@RestController
class CheckoutRestController {
    private final CheckoutUseCase checkoutUseCase;

    CheckoutRestController(CheckoutUseCase checkoutUseCase) {
        this.checkoutUseCase = checkoutUseCase;
    }

    @PostMapping("/checkout/sessions")
    ResponseEntity<CheckoutSession> start(@RequestBody StartCheckoutRequest request) {
        return ResponseEntity.accepted().body(checkoutUseCase.startCheckout(request.tenantId(), request.patientId(), request.amount()));
    }

    @GetMapping("/checkout/sessions/{id}")
    ResponseEntity<CheckoutSession> status(@PathVariable Long id) {
        return ResponseEntity.of(checkoutUseCase.status(id));
    }

    record StartCheckoutRequest(Long tenantId, Long patientId, BigDecimal amount) {
    }
}

