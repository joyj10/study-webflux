package org.study.webflux.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.study.webflux.dto.PostResponse;
import org.study.webflux.service.PostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostContent(@PathVariable Long id) {
        return postService.getPostContent(id);
    }


    @GetMapping("/multiple")
    public Flux<PostResponse> getMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getMultiplePostContent(idList);
    }

    @GetMapping("/parallel-multiple")
    public Flux<PostResponse> getParallelMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getParallelMultiplePostContent(idList);
    }
}
