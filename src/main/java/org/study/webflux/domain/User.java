package org.study.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
