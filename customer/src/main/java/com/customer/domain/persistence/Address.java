package com.customer.domain.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @Column(name = "state", length = 50, nullable = false)
    private String state;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "street_number", length = 10, nullable = false)
    private String streetNumber;

    @Column(name = "apartment", length = 10)
    private String apartment;

    @Column(name = "floor", length = 5)
    private String floor;

    @Column(name = "additional_info", length = 200)
    private String additionalInfo;

}