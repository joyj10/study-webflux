package org.study.webflux.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.study.webflux.domain.User;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @DisplayName("사용자를 저장한다.")
    @Test
    void save() {
        var user = User.builder().name("test").email("test@test.co.kr").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("test", u.getName());
                })
                .verifyComplete();
    }

    @DisplayName("사용자 전체 정보를 조회한다.")
    @Test
    void findAll() {
        userRepository.save(User.builder().name("test").email("test@test.co.kr").build());
        userRepository.save(User.builder().name("test2").email("test2@test.co.kr").build());
        userRepository.save(User.builder().name("test3").email("test3@test.co.kr").build());

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @DisplayName("사용자를 단건 조회한다.")
    @Test
    void findById() {
        userRepository.save(User.builder().name("test").email("test@test.co.kr").build());
        userRepository.save(User.builder().name("test2").email("test2@test.co.kr").build());

        StepVerifier.create(userRepository.findById(2L))
                .assertNext(u -> {
                    assertEquals(2L, u.getId());
                    assertEquals("test2", u.getName());
                })
                .verifyComplete();
    }

    @DisplayName("사용자를 삭제한다.")
    @Test
    void deleteById() {
        userRepository.save(User.builder().name("test").email("test@test.co.kr").build());
        userRepository.save(User.builder().name("test2").email("test2@test.co.kr").build());

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNextCount(1)
                .verifyComplete();
    }
}
