package com.b1uffer.multisessiontest.config;

import com.b1uffer.multisessiontest.custom.CustomUserDetailsService;
import com.b1uffer.multisessiontest.support.InMemoryUsers;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 동시 세션 제어 핵심
    @Bean
    @Order(1)
    public SecurityFilterChain sessionFilterChain(HttpSecurity http, DataSource dataSource, CustomUserDetailsService customUserDetailsService) throws Exception {
        http
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/session-expired", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation(fixation -> fixation.migrateSession()) // 세션 고정 공격에 대한 보호
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/session-expired")
                        .sessionRegistry(sessionRegistry())
                )
                .rememberMe(remember -> remember
                        /**
                        * TokenBasedRememberMeServices, DataSource 주입이 필요하지 않음
                        */
                        .key("my-remember-key") // 쿠키 생성 시 사용되는 고정 키
                        .rememberMeCookieName("my-remember-me") // 쿠키의 이름
                        .rememberMeParameter("remember-me") // 로그인 폼에서 사용하는 파라미터명
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // 쿠키 만료 시간
                        .userDetailsService(customUserDetailsService) // 사용자 검증 서비스 추가
                        /**
                         * PersistentTokenBasedRememberMeServices, FilterChain 에 DataSource 주입이 필요함
                         */
//                        .tokenRepository(persistentTokenRepository(dataSource))
                );
        return http.build();
    }

    @Bean
    @Order(0)
    public SecurityFilterChain h2FilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(PathRequest.toH2Console())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * PersistentTokenBasedRememberMeService Repository 구현체
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource datasource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(datasource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
