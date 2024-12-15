package com.mealpick.mealpickServer.domain.user.service

import com.mealpick.mealpickServer.domain.user.controller.request.JoinRequest
import com.mealpick.mealpickServer.domain.user.controller.request.LoginRequest
import com.mealpick.mealpickServer.domain.user.model.User
import com.mealpick.mealpickServer.domain.user.model.constant.SEX
import com.mealpick.mealpickServer.domain.user.persistence.UserPersistence
import com.mealpick.mealpickServer.global.annotation.service.CommandService
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import com.mealpick.mealpickServer.global.config.security.jwt.JwtUtil
import com.mealpick.mealpickServer.global.config.security.jwt.dto.TokenDto
import org.springframework.security.crypto.password.PasswordEncoder

@CommandService
class CommandUserService (
    val userPersistence: UserPersistence,
    val jwtUtil: JwtUtil,
    val passwordEncoder: PasswordEncoder
) {
    fun login(loginRequest: LoginRequest): TokenDto {
        val user = userPersistence.findByEmail(loginRequest.email!!)

        if (!passwordEncoder.matches(loginRequest.password, user.password)) throw CustomException(ErrorCode.USER_NOT_FOUND)

        return jwtUtil.generate(user.id!!)
    }

    fun socialLogin(loginRequest: LoginRequest): TokenDto {
        val socialEmail = userPersistence.getSocialEmail(loginRequest.token!!, loginRequest.provider!!)
        val user = userPersistence.findByEmail(socialEmail)

        return jwtUtil.generate(user.id!!)
    }

    fun localJoin(joinRequest: JoinRequest) {
        if (!userPersistence.existByEmail(joinRequest.email!!)) throw CustomException(ErrorCode.USER_CONFLICT)

        val encodedPassword = passwordEncoder.encode(joinRequest.password!!)

        val user = User(
            name = "익명의 사용자",
            email = joinRequest.email,
            provider = joinRequest.provider,
            password = encodedPassword,
            sex = null,
            age = null,
            cantFoods = null,
            hateFoods = null,
            likeFoods = null
        )

        userPersistence.persist(user)
    }

    fun socialJoin(joinRequest: JoinRequest) {
        val socialUserInfo = userPersistence.getSocialUserInfo(joinRequest.token!!, joinRequest.provider!!)

        if (!userPersistence.existByEmail(socialUserInfo.email)) throw CustomException(ErrorCode.USER_CONFLICT)

        val gender = when (socialUserInfo.gender) {
            "male" -> SEX.MAN
            "female" -> SEX.WOMAN
            else -> SEX.DONT_WANT
        }

        val age = parseAgeFromRange(socialUserInfo.ageRange)

        val user = User(
            name = socialUserInfo.nickname ?: "익명의 사용자",
            provider = joinRequest.provider,
            email = socialUserInfo.email,
            password = null,
            sex = gender,
            age = age,
            cantFoods = null,
            hateFoods = null,
            likeFoods = null
        )

        userPersistence.persist(user)
    }

    private fun parseAgeFromRange(ageRange: String?): Int? {
        if (ageRange.isNullOrEmpty()) return null

        val range = ageRange.split("~")
        if (range.size != 2) {
            throw IllegalArgumentException("Invalid age range format: $ageRange")
        }

        val lowerBound = range[0].toIntOrNull()
        val upperBound = range[1].toIntOrNull()

        if (lowerBound == null || upperBound == null) {
            throw IllegalArgumentException("Invalid number format in age range: $ageRange")
        }

        // 평균값 계산
        return (lowerBound + upperBound) / 2
    }
}
