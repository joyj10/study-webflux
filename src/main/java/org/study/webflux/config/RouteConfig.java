package org.study.webflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.study.webflux.SimpleHandler;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {
    private final SimpleHandler simpleHandler;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/hello-functional", simpleHandler::getString)
                .build();
    }
}
