package org.study.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @GetMapping("/hello-annotation")
    public Mono<String> getHello() {
        return Mono.just("hello, annotation");
    }
}
