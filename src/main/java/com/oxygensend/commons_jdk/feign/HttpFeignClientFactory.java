package com.oxygensend.commons_jdk.feign;

import feign.Client;
import feign.hc5.ApacheHttp5Client;
import java.util.List;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;

final class HttpFeignClientFactory implements FeignClientFactory {

    private final CloseableHttpClient httpClient;

    HttpFeignClientFactory(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Client uriFeignClient() {
        return new ApacheHttp5Client(httpClient);
    }

    @Override
    public Client eurekaFeignClient(LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory) {
        var delegate = new ApacheHttp5Client(httpClient);
        return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory, List.of());
    }
}
