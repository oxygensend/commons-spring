package com.oxygensend.commonspring.request_context;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

record HttpRequestContext(String userIdRaw,
                          List<String> authorities,
                          String requestId) implements RequestContext {
    static HttpRequestContext EMPTY = new HttpRequestContext(null, List.of(), randomAlphabetic(10));

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userIdRaw);
    }

}
