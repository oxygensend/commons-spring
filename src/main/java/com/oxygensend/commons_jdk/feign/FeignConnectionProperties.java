package com.oxygensend.commons_jdk.feign;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "feign.connection")
@Validated
public record FeignConnectionProperties(@NotNull ConnectionType type) {
}
