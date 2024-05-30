package com.oxygensend.commonspring.feign;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

public final class ClientFactory {

    private final FeignConnectionProperties connectionProperties;
    private final ApplicationContext applicationContext;

    ClientFactory(FeignConnectionProperties properties, ApplicationContext applicationContext) {
        this.connectionProperties = properties;
        this.applicationContext = applicationContext;
    }

    public <T> T create(String url, Class<T> clazz, FeignBuilderCustomizer customizer) {
        return switch (connectionProperties.type()) {
            case URI -> new FeignClientBuilder(applicationContext).forType(clazz, url)
                                                                  .customize(customizer)
                                                                  .build();
            case SERVICE_DISCOVERY -> new FeignClientBuilder(applicationContext).forType(clazz, clazz.getName())
                                                                                .customize(customizer)
                                                                                .url(url)
                                                                                .build();
        };
    }

}
