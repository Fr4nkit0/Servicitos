package com.commons.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCustomerDetail(
                Long id,
                String name,
                @JsonProperty(value = "last_name") String lastName,
                String email,
                String phone,
                GetAddress address,
                @JsonProperty(value = "create_at") LocalDateTime created_at,
                @JsonProperty(value = "update_at") LocalDateTime update_at) implements Serializable {

}
