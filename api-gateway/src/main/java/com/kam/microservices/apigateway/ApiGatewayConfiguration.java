package com.kam.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gateWayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p.path("/get")
                .filters(f -> f.addRequestHeader("MyHeader", "MyURI"))
                .uri("http://httpbin.org.80"))
            .route(p -> p.path("/currency-exchange/**")
                    .uri((String) "lb://currency-exchange"))
            .route(p -> p.path("/currency-conversion/**")
                    .uri((String) "lb://currency-exchange"))
            .route(p -> p.path("/currency-exchange-feign/**")
                    .uri((String) "lb://currency-exchange"))
            .route(p -> p.path("/currency-conversion-new/**")
                    .filters(f -> f.rewritePath(
                            "/currency-conversion-new/(?<segment>.*)",
                            "/currency-conversion-new/${segment}"))
                    .uri((String) "lb://currency-conversion"))
                .build();
    }
}
