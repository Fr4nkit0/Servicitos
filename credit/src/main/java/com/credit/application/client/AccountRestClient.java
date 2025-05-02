package com.credit.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.commons.dto.request.Deposito;
import com.commons.dto.response.GetAccount;

@FeignClient(name = "${feign.clients.account.name}", url = "${feign.clients.account.url}/api/v1/accounts")
public interface AccountRestClient {

    ResponseEntity<GetAccount> despositInAccount(@RequestBody Deposito deposito);

}
