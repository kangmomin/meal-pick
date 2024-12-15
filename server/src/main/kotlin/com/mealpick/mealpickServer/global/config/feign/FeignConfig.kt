package com.mealpick.mealpickServer.global.config.feign

import feign.codec.ErrorDecoder

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FeignConfig {
    @Bean
    @ConditionalOnMissingBean(ErrorDecoder::class)
    fun commonFeignErrorDecoder(): FeignClientErrorDecoder {
        return FeignClientErrorDecoder()
    }
}