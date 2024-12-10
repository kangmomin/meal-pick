package com.mealpick.mealpickServer.domain.user.controller

import com.mealpick.mealpickServer.domain.user.controller.request.LoginRequest
import com.mealpick.mealpickServer.domain.user.controller.response.TokenResponse
import com.mealpick.mealpickServer.domain.user.service.CommandUserService
import com.mealpick.mealpickServer.global.common.response.MsgResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController (
    private val commandUserService: CommandUserService
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest
    ): TokenResponse {
        val tokenDto = commandUserService.login(loginRequest);

        return TokenResponse(tokenDto.accessToken, tokenDto.refreshToken)
    }
}