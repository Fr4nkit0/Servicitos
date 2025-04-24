package com.customer.application.dto.request;

import java.io.Serializable;

import com.customer.application.valid.ValidMobile;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaveCustomer(
                @Size(min = 4, max = 100, message = "{generic.size}") @NotBlank(message = "{generic.notBlank}") String name,
                @Size(min = 4, max = 100, message = "{generic.size}") @NotBlank(message = "{generic.notBlank}") String lastName,
                @Email(message = "invalidEmail") @NotBlank(message = "{generic.notBlank}") String email,
                @NotBlank(message = "{generic.notBlank}") @ValidMobile String mobile,
                @NotNull(message = "{generic.notNull}") SaveAddress address) implements Serializable {
        public record SaveAddress(
                        @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String country,

                        @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String state,

                        @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 50, message = "{generic.size}") String city,

                        @JsonProperty(value = "postal_code") @NotBlank(message = "{generic.notBlank}") @Size(min = 2, max = 20, message = "{generic.size}") String postalCode,

                        @NotBlank(message = "{generic.notBlank}") @Size(min = 4, max = 100, message = "{generic.size}") String street,

                        @JsonProperty(value = "street_number") @NotBlank(message = "{generic.notBlank}") @Size(min = 5, max = 10, message = "{generic.size}") String streetNumber,
                        @Size(max = 10, message = "{generic.max}") String apartment,
                        @Size(max = 5, message = "{generic.max}") String floor,
                        @JsonProperty(value = "additional_info") @Size(max = 200, message = "{generic.max}") String additionalInfo) {
        }
}
