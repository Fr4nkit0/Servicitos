package com.commons.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCustomerDetail(
        Long id,
        String name,
        @JsonProperty(value = "last_name") String lastName,
        String email,
        String phone,
        GetAddress address) implements Serializable {

}
