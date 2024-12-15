package com.mealpick.mealpickServer.domain.user.controller

import com.mealpick.mealpickServer.domain.user.controller.request.JoinRequest
import com.mealpick.mealpickServer.domain.user.controller.request.LoginRequest
import com.mealpick.mealpickServer.domain.user.controller.response.TokenResponse
import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider
import com.mealpick.mealpickServer.domain.user.service.CommandUserService
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import com.mealpick.mealpickServer.global.common.response.MsgResponse
import com.mealpick.mealpickServer.global.config.security.jwt.dto.TokenDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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
        val tokenDto: TokenDto?
        when (loginRequest.provider!!) {
            LoginProvider.LOCAL -> {
                if (loginRequest.email.isNullOrBlank()
                    || loginRequest.email.length < 3) throw CustomException(ErrorCode.USER_EMAIL_NOT_VALID)
                if (loginRequest.password.isNullOrBlank()
                    || loginRequest.password.length < 8) throw CustomException(ErrorCode.USER_PASSWORD_NOT_VALID)

                tokenDto = commandUserService.login(loginRequest)
            }

            else -> {
                if (loginRequest.token.isNullOrBlank()
                    || loginRequest.token.length < 8) throw CustomException(ErrorCode.USER_PASSWORD_NOT_VALID)
                tokenDto = commandUserService.socialLogin(loginRequest)
            }
        }

        return TokenResponse(tokenDto.accessToken, tokenDto.refreshToken)
    }

    @PostMapping("/join")
    fun join(
        @RequestBody @Valid joinRequest: JoinRequest
    ): MsgResponse {
        when (joinRequest.provider!!) {
            LoginProvider.LOCAL -> {
                if (joinRequest.email.isNullOrBlank()
                    || joinRequest.email.length < 3) throw CustomException(ErrorCode.USER_EMAIL_NOT_VALID)
                if (joinRequest.password.isNullOrBlank()
                    || joinRequest.password.length < 8) throw CustomException(ErrorCode.USER_PASSWORD_NOT_VALID)

                commandUserService.localJoin(joinRequest)
            }

            else -> {
                if (joinRequest.token.isNullOrBlank()) throw CustomException(ErrorCode.USER_OAUTH_TOKEN_NOT_VALID)
                commandUserService.socialJoin(joinRequest)
            }
        }

        return MsgResponse("성공적으로 회원가입 되었습니다.")
    }
}