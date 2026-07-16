package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface CheckoutRepository extends JpaRepository<CheckoutEntity, Long> {
}

