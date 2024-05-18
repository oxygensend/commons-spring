package com.oxygensend.commons_jdk.request_context;

import java.util.List;
import java.util.Optional;

record HttpRequestContext(String userIdRaw,
                          List<String> authorities) implements RequestContext {
    static HttpRequestContext EMPTY = new HttpRequestContext(null, List.of());

    @Override
    public Optional<String> userId() {
        return Optional.ofNullable(userIdRaw);
    }

}
