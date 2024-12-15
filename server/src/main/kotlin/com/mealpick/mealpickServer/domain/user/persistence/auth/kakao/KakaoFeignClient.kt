package com.mealpick.mealpickServer.domain.user.persistence.auth.kakao

import com.mealpick.mealpickServer.domain.user.persistence.auth.kakao.dto.AccessTokenResponse
import com.mealpick.mealpickServer.domain.user.persistence.auth.kakao.dto.UserInfoResponse
import com.mealpick.mealpickServer.global.config.feign.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakaoFeignClient",
    url = "https://kapi.kakao.com",
    configuration = [FeignConfig::class])
interface KakaoFeignClient {

    @PostMapping("/oauth/token")
    fun getAccessToken(@RequestParam("token") token: String): AccessTokenResponse

    @GetMapping("/v2/user/me")
    fun getUserInfo(@RequestHeader("Authorization") accessToken: String): UserInfoResponse
}
