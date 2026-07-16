package com.gokhantamkoc.microservicetraining.healthcare.physician.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PhysicianDomainTest {
    @Test
    void physicianKeepsProfessionalProfile() {
        Physician physician = new Physician(1L, 10L, "Dr. Ada Lovelace", "Cardiology", true);

        assertThat(physician.id()).isEqualTo(1L);
        assertThat(physician.tenantId()).isEqualTo(10L);
        assertThat(physician.fullName()).isEqualTo("Dr. Ada Lovelace");
        assertThat(physician.specialty()).isEqualTo("Cardiology");
        assertThat(physician.active()).isTrue();
    }

    @Test
    void physiciansWithSameValuesAreEqual() {
        Physician first = new Physician(1L, 10L, "Dr. Ada Lovelace", "Cardiology", true);
        Physician second = new Physician(1L, 10L, "Dr. Ada Lovelace", "Cardiology", true);

        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }
}

