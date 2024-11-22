package org.study.webflux.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    // R2DBC DB 연결 초기화 및 검증 : DB connection 정상 여부를 애플리케이션 시작 시점에 확인
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // reactor : publisher, subscriber
        databaseClient.sql("SELECT 1").fetch().one()
                .subscribe(
                        success -> log.info("Initialize r2dbc database connection."),
                        error -> {
                            log.error("Failed to initialize r2dbc database connection.");
                            // 연결 정상적이지 않으면 애플리케이션 종료 (응답코드 -110)
                            SpringApplication.exit(event.getApplicationContext(), () -> -110);
                        }
                );

    }
}
