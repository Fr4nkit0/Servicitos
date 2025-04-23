package com.customer.application.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResponse(
        @JsonProperty("http_code") int httCode,
        String url,
        @JsonProperty("http_method") String httpMethod,
        String message,
        @JsonProperty("backend_message") String backendMessage,
        @JsonProperty("times_tamp") LocalDateTime timeTamp,
        List<String> details) implements Serializable {

}
