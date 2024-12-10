package com.mealpick.mealpickServer.global.config.security

import com.mealpick.mealpickServer.global.config.security.filter.JwtAuthFilter
import com.mealpick.mealpickServer.global.config.security.filter.SecurityExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SpringSecurity (
    private val jwtAuthFilter: JwtAuthFilter,
    private val securityExceptionHandler: SecurityExceptionHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity) =
        http.csrf { it.disable() }
            .cors {  }
            .authorizeHttpRequests {
                it.requestMatchers(RequestMatcher {req ->
                    CorsUtils.isCorsRequest(req)
                }).permitAll()

                it.anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(securityExceptionHandler, JwtAuthFilter::class.java)
            .build()!!

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}