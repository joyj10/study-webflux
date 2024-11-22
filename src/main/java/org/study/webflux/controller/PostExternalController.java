package org.study.webflux.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.study.webflux.dto.PostExternalResponse;
import org.study.webflux.service.PostExternalService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/external")
public class PostExternalController {

    private final PostExternalService postExternalService;

    @GetMapping("/{id}")
    public Mono<PostExternalResponse> getPostContent(@PathVariable Long id) {
        return postExternalService.getPostContent(id);
    }


    @GetMapping("/multiple")
    public Flux<PostExternalResponse> getMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postExternalService.getMultiplePostContent(idList);
    }

    @GetMapping("/parallel-multiple")
    public Flux<PostExternalResponse> getParallelMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postExternalService.getParallelMultiplePostContent(idList);
    }
}
