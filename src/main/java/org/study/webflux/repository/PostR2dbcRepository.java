package org.study.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.study.webflux.domain.Post;

public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long> {
}
