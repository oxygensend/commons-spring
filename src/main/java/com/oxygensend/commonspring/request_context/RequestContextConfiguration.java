package com.oxygensend.commonspring.request_context;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Configuration
public class RequestContextConfiguration {
    private static final String AUTHORITIES_ATTRIBUTE = "X-AUTHORITIES";
    private static final String USER_ID_ATTRIBUTE = "X-USER-ID";
    private static final String REQUEST_ID_ATTRIBUTE = "Request-Id";


    @Bean
    @RequestScope
    RequestContext httpRequestContext(HttpServletRequest request) {
        List<String> authorities = request.getHeader(AUTHORITIES_ATTRIBUTE) != null ?
                                   List.of(request.getHeader(AUTHORITIES_ATTRIBUTE).split(",")) : List.of();
        var userId = request.getHeader(USER_ID_ATTRIBUTE);
        var requestId = request.getHeader(REQUEST_ID_ATTRIBUTE) != null ? request.getHeader(REQUEST_ID_ATTRIBUTE) : "[" + randomAlphabetic(10) + "]";

        MDC.put(REQUEST_ID_ATTRIBUTE, requestId);
        return new HttpRequestContext(userId, authorities, requestId);
    }
}
