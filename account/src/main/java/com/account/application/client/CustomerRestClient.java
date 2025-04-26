package com.account.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetCustomerDetail;

@FeignClient(name = "${feign.clients.customer.name}", url = "${feign.clients.customer.url}")
public interface CustomerRestClient {

    @PostMapping
    ResponseEntity<GetCustomerDetail> save(@RequestBody SaveCustomer saveCustomer);

    @GetMapping
    ResponseEntity<GetCustomerDetail> findByEmail(@RequestParam String email);

}
