package org.study.webflux.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.study.webflux.dto.UserCreateRequest;
import org.study.webflux.dto.UserPostResponse;
import org.study.webflux.dto.UserResponse;
import org.study.webflux.dto.UserUpdateRequest;
import org.study.webflux.service.PostService;
import org.study.webflux.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @PostMapping
    public Mono<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        return userService.create(request.getName(), request.getEmail())
                .map(UserResponse::of);
    }

    @GetMapping
    public Flux<UserResponse> findAllUsers() {
        return userService.findAll()
                .map(UserResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> findUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/name")
    public Mono<ResponseEntity<Void>> deleteUserByName(@RequestParam String name) {
        return userService.deleteByName(name).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        // user (x): 404 not found
        // user (o): 200 ok
        return userService.update(id, request.getName(), request.getEmail())
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}/posts")
    public Flux<UserPostResponse> getUserPosts(@PathVariable Long id) {
        return postService.findAllByUserId(id)
                .map(UserPostResponse::of);

    }
}
