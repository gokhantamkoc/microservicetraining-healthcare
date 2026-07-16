package com.gokhantamkoc.microservicetraining.healthcare.notification.domain;

public enum NotificationProvider {
    AWS_SNS("notification.aws-sns"),
    TWILIO("notification.twilio"),
    FCM("notification.fcm");

    private final String routingKey;

    NotificationProvider(String routingKey) {
        this.routingKey = routingKey;
    }

    public String routingKey() {
        return routingKey;
    }
}

