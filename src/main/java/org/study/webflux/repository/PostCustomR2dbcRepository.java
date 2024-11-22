package org.study.webflux.repository;

import org.study.webflux.domain.Post;
import reactor.core.publisher.Flux;

public interface PostCustomR2dbcRepository {
    Flux<Post> findAllByUserId(Long userId);
}
