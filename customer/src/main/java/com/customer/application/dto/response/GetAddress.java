package com.customer.application.dto.response;

import java.io.Serializable;

public record GetAddress(
        String country,
        String state,
        String city,
        String postalCode,
        String street,
        String streetNumber,
        String apartment,
        String floor,
        String additionalInfo) implements Serializable {
}
