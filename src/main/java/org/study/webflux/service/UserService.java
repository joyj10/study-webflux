package org.study.webflux.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.study.webflux.domain.User;
import org.study.webflux.repository.UserR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
//    private final UserRepository userRepository;
    private final UserR2dbcRepository userRepository;
    private final ReactiveRedisTemplate<String, User> reactiveRedisTemplate;

    public Mono<User> create(String name, String email) {
        return userRepository.save(User.builder().name(name).email(email).build());
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        String userCacheKey = getUserCacheKey(id);
        // 1. redis 조회
        // 2. 캐싱 데이터 존재 시 응답
        // 3. 없으면 DB에 질의 후 redis에 저장 후 리턴

        return reactiveRedisTemplate.opsForValue()
                .get(userCacheKey)
                .switchIfEmpty(userRepository.findById(id)
                        .flatMap(u -> reactiveRedisTemplate.opsForValue()
                                .set(userCacheKey, u)
                                .then(Mono.just(u))));
    }

    private String getUserCacheKey(Long id) {
        return "users:%d".formatted(id);
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id)
                .then(reactiveRedisTemplate.unlink(getUserCacheKey(id)))
                .then(Mono.empty());
    }

    public Mono<Void> deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    public Mono<User> update(Long id, String name, String email) {
        return userRepository.findById(id)
                .flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userRepository.save(u);
                })
                .flatMap(u -> reactiveRedisTemplate.unlink(getUserCacheKey(id)).then(Mono.just(u)));
    }
}
