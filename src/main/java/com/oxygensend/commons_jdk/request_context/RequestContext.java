package com.oxygensend.commons_jdk.request_context;

import java.util.List;
import java.util.Optional;

public interface RequestContext {
    Optional<Long> userId();

    List<String> authorities();

    default String userIdAsString() {
        return userId().map(String::valueOf).orElse("Unknown");
    }

    default boolean hasAuthority(String authority) {
        return authorities().contains(authority);
    }


    default boolean isUserAuthenticated(Long userId) {
        return userId().isPresent() && userId().get().equals(userId);
    }

}
