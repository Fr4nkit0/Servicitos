package com.commons.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetAddress(
        String country,
        String state,
        String city,
        @JsonProperty(value = "postal_code") String postalCode,
        String street,
        @JsonProperty(value = "street_number") String streetNumber,
        String apartment,
        String floor,
        @JsonProperty(value = "additional_info") String additionalInfo) implements Serializable {
}