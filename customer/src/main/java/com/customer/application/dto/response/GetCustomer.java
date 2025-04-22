package com.customer.application.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCustomer(
        String name,
        @JsonProperty(value = "last_name") String lastName,
        String email,
        String phone) implements Serializable {

}
