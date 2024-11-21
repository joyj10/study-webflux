package org.study.webflux.repository;

import org.study.webflux.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(Long id);

    Mono<Integer> deleteById(Long id);
}