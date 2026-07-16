package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.in.graphql;

import java.math.BigDecimal;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.application.CheckoutUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutSession;

@Controller
class CheckoutGraphqlController {
    private final CheckoutUseCase checkoutUseCase;

    CheckoutGraphqlController(CheckoutUseCase checkoutUseCase) {
        this.checkoutUseCase = checkoutUseCase;
    }

    @MutationMapping
    CheckoutSession startCheckout(@Argument Long tenantId, @Argument Long patientId, @Argument BigDecimal amount) {
        return checkoutUseCase.startCheckout(tenantId, patientId, amount);
    }

    @QueryMapping
    CheckoutSession checkoutStatus(@Argument Long id) {
        return checkoutUseCase.status(id).orElse(null);
    }
}

