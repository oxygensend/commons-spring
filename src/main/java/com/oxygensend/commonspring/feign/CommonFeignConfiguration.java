package com.oxygensend.commonspring.feign;

import com.oxygensend.commonspring.request_context.RequestContext;
import feign.Client;
import feign.RequestInterceptor;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeignConnectionProperties.class)
public class CommonFeignConfiguration {


    @Bean
    FeignClientFactory httpClientFactory() {
        return new HttpFeignClientFactory(HttpClients.createDefault());
    }

    @Bean
    ClientFactory feignClientFactory(FeignConnectionProperties properties, ApplicationContext applicationContext) {
        return new ClientFactory(properties, applicationContext);
    }

    @Bean
    Client uriFeignClient(FeignConnectionProperties properties, FeignClientFactory feignClientFactory,
                          LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory) {
        return switch (properties.type()) {
            case SERVICE_DISCOVERY -> feignClientFactory.eurekaFeignClient(loadBalancerClient, loadBalancerClientFactory);
            case URI -> feignClientFactory.uriFeignClient();
        };
    }

    @Bean
    RequestInterceptor requestIdHeaderPropagation(RequestContext requestContext) {
        return requestTemplate -> {
            requestTemplate.header("Request-Id", requestContext.requestId());
        };
    }

}
