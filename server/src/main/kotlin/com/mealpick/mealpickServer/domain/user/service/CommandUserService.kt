package com.mealpick.mealpickServer.domain.user.service

import com.mealpick.mealpickServer.domain.user.controller.request.LoginRequest
import com.mealpick.mealpickServer.domain.user.persistence.UserPersistence
import com.mealpick.mealpickServer.global.annotation.service.CommandService
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import com.mealpick.mealpickServer.global.config.security.jwt.JwtUtil
import com.mealpick.mealpickServer.global.config.security.jwt.dto.TokenDto
import org.codehaus.groovy.syntax.TokenUtil
import org.springframework.security.crypto.password.PasswordEncoder

@CommandService
class CommandUserService (
    val userPersistence: UserPersistence,
    val jwtUtil: JwtUtil,
    val passwordEncoder: PasswordEncoder
) {
    fun login(loginRequest: LoginRequest): TokenDto {
        val user = userPersistence.findByEmail(loginRequest.email)

        if (!passwordEncoder.matches(loginRequest.password, user.password)) throw CustomException(ErrorCode.USER_NOT_FOUND)

        return jwtUtil.generate(user.id!!)
    }
}
