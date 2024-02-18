package com.oxygensend.commons_jdk.feign;

import feign.Client;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;

public interface FeignClientFactory {
    Client uriFeignClient();

    Client eurekaFeignClient(LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory);

}
