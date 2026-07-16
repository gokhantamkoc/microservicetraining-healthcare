package com.gokhantamkoc.microservicetraining.healthcare.checkout.application;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.grpc.PatientValidationClient;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.messaging.NotificationCommandPublisher;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.persistence.CheckoutPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutSession;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutStatus;

@Service
@Transactional
public class CheckoutSagaService implements CheckoutUseCase {
    private final CheckoutPersistenceAdapter persistence;
    private final PatientValidationClient patientValidationClient;
    private final NotificationCommandPublisher notificationCommandPublisher;

    public CheckoutSagaService(
            CheckoutPersistenceAdapter persistence,
            PatientValidationClient patientValidationClient,
            NotificationCommandPublisher notificationCommandPublisher) {
        this.persistence = persistence;
        this.patientValidationClient = patientValidationClient;
        this.notificationCommandPublisher = notificationCommandPublisher;
    }

    @Override
    public CheckoutSession startCheckout(Long tenantId, Long patientId, BigDecimal amount) {
        CheckoutSession started = persistence
                .save(new CheckoutSession(null, tenantId, patientId, amount, CheckoutStatus.STARTED));
        if (!patientValidationClient.patientExists(patientId)) {
            return persistence.updateStatus(started.id(), CheckoutStatus.FAILED);
        }
        CheckoutSession paid = persistence.updateStatus(started.id(), CheckoutStatus.PAID);
        notificationCommandPublisher.publishPaymentReceipt(tenantId, patientId, "Medical bill paid: " + amount);
        return paid;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckoutSession> status(Long id) {
        return persistence.findById(id);
    }
}
