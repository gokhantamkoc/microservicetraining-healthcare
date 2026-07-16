package com.gokhantamkoc.microservicetraining.healthcare.notification.domain;

public record NotificationOperationStatus(String operationId, String status, NotificationDelivery delivery, String errorMessage) {
}

