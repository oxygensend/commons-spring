package com.oxygensend.commonspring.storage;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "storage")
record StorageProperties(@NotEmpty String rootLocation) {
}
