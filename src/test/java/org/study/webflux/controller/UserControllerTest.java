package org.study.webflux.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.study.webflux.domain.User;
import org.study.webflux.dto.UserCreateRequest;
import org.study.webflux.dto.UserResponse;
import org.study.webflux.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @DisplayName("사용자를 등록한다.")
    @Test
    void createUser() {
        when(userService.create("testuser", "testuser@test.co.kr")).thenReturn(
                Mono.just(new User(1L, "testuser", "testuser@test.co.kr", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("testuser", "testuser@test.co.kr"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("testuser", res.getName());
                    assertEquals("testuser@test.co.kr", res.getEmail());
                });
    }

    @DisplayName("모든 사용자를 조회한다.")
    @Test
    void findAllUsers() {
        when(userService.findAll()).thenReturn(
                Flux.just(
                        new User(1L, "testuser1", "testuser1@test.co.kr", LocalDateTime.now(), LocalDateTime.now()),
                        new User(2L, "testuser2", "testuser2@test.co.kr", LocalDateTime.now(), LocalDateTime.now()),
                        new User(3L, "testuser3", "testuser3@test.co.kr", LocalDateTime.now(), LocalDateTime.now())
                ));

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @DisplayName("사용자를 조회한다.")
    @Test
    void findUser() {
        when(userService.findById(1L)).thenReturn(
                Mono.just(new User(1L, "testuser", "testuser@test.co.kr", LocalDateTime.now(), LocalDateTime.now())
                ));

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("testuser", res.getName());
                    assertEquals("testuser@test.co.kr", res.getEmail());
                });
    }

    @DisplayName("사용자 조회 시 데이터가 없는 경우 NotFound 리턴한다.")
    @Test
    void notFoundUser() {
        when(userService.findById(1L)).thenReturn(Mono.empty());

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @DisplayName("사용자를 삭제한다.")
    @Test
    void deleteUser() {
        when(userService.deleteById(1L)).thenReturn(Mono.just(1));

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @DisplayName("사용자를 수정한다.")
    @Test
    void updateUser() {
        when(userService.update(1L, "testuser1", "testuser1@test.co.kr")).thenReturn(
                Mono.just(new User(1L, "testuser1", "testuser1@test.co.kr", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("testuser1", "testuser1@test.co.kr"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("testuser1", res.getName());
                    assertEquals("testuser1@test.co.kr", res.getEmail());
                });
    }
}
