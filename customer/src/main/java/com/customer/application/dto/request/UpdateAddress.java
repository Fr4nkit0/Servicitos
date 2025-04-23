package com.customer.application.dto.request;

import java.io.Serializable;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateAddress(
                @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String country,
                @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String state,
                @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String city,
                @NotBlank(message = "{generic.notBlank}") @Size(min = 2, max = 20, message = "{generic.size}") String postalCode,
                @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String street,
                @NotBlank(message = "{generic.notBlank}") @Size(min = 1, max = 50, message = "{generic.size}") String streetNumber,
                @Size(max = 10, message = "{generic.max}") Optional<String> apartment,
                @Size(max = 5, message = "{generic.max}") Optional<String> floor,
                @Size(max = 200, message = "{generic.max}") Optional<String> additionalInfo,
                @Positive(message = "{generic.positive}") Long customerId) implements Serializable {

}
