package com.oxygensend.commons_jdk.request_context;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RequestContextConfiguration {
    private static final String AUTHORITIES_ATTRIBUTE = "X-AUTHORITIES";
    private static final String USER_ID_ATTRIBUTE = "X-USER-ID";


    @Bean
    @RequestScope
    RequestContext httpRequestContext(HttpServletRequest request) {
        var authorities = request.getHeader(AUTHORITIES_ATTRIBUTE);
        var userId = request.getHeader(USER_ID_ATTRIBUTE);
        if (userId == null || authorities == null) {
            return HttpRequestContext.EMPTY;
        }

        return new HttpRequestContext(userId, List.of(authorities.split(",")));
    }
}
