package org.study.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.study.webflux.domain.User;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
}
