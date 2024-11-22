package org.study.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.study.webflux.domain.Post;
import reactor.core.publisher.Flux;

public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long>, PostCustomR2dbcRepository {
    Flux<Post> findAllByUserId(Long userId);
}
