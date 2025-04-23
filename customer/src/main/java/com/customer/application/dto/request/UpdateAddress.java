package com.customer.application.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateAddress(
        @Size(min = 4, max = 50, message = "{generic.size}") String country,
        @Size(min = 4, max = 50, message = "{generic.size}") String state,
        @Size(min = 4, max = 50, message = "{generic.size}") String city,
        @Size(min = 2, max = 20, message = "{generic.size}") String postalCode,
        @Size(min = 4, max = 50, message = "{generic.size}") String street,
        @Size(min = 1, max = 50, message = "{generic.size}") String streetNumber,
        @Size(max = 10, message = "{generic.max}") String apartment,
        @Size(max = 5, message = "{generic.max}") String floor,
        @Size(max = 200, message = "{generic.max}") String additionalInfo,
        @Positive(message = "{generic.positive}") Long customerId) implements Serializable {

}
