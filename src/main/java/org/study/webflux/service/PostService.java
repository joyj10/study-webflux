package org.study.webflux.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.study.webflux.client.PostClient;
import org.study.webflux.dto.PostResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostClient postClient;

    // 단건 요청
    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(new PostResponse(id.toString(), "Fallback data %d".formatted(id)))); // 오류 발생 시 전체 실패가 아닌 일부 실패 데이터 처리
    }

    // 다건 요청
    public Flux<PostResponse> getMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPostContent)
                .log();
    }

    // 병렬 요청
    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();
    }
}
