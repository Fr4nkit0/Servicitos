package com.customer.application.dto.request;

import java.io.Serializable;

import com.commons.valid.ValidMobile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateCustomer(
        @Size(min = 4, max = 100, message = "{generic.size}") String name,
        @Size(min = 4, max = 100, message = "{generic.size}") String lastName,
        @Email(message = "invalidEmail") String email,
        @ValidMobile String mobile) implements Serializable {

}
