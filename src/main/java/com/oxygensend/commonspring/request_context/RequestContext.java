package com.oxygensend.commonspring.request_context;

import java.util.List;
import java.util.Optional;

public interface RequestContext {
    Optional<String> userId();

    List<String> authorities();

    default String userIdAsString() {
        return userId().orElse("Unknown");
    }

    default boolean hasAuthority(String authority) {
        return authorities().contains(authority);
    }


    default boolean isUserAuthenticated(Long userId) {
        return userId().isPresent() && userId().get().equals(userId.toString());
    }

    default boolean isUserAuthenticated(String userId) {
        return userId().isPresent() && userId().get().equals(userId);
    }

    default boolean isAuthorized() {
        return userId().isPresent();
    }

}
