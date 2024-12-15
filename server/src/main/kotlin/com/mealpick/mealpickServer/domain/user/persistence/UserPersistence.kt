package com.mealpick.mealpickServer.domain.user.persistence

import com.mealpick.mealpickServer.domain.user.mapper.UserMapper
import com.mealpick.mealpickServer.domain.user.model.User
import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider
import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider.*
import com.mealpick.mealpickServer.domain.user.persistence.auth.SocialUserInfoDto
import com.mealpick.mealpickServer.domain.user.persistence.auth.kakao.KakaoSocialLoginApi
import com.mealpick.mealpickServer.domain.user.persistence.repository.UserRepository
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserPersistence (
    private val userRepository: UserRepository,
    private val kakaoSocialLoginApi: KakaoSocialLoginApi
) {

    fun findById(id: UUID) = userRepository.findByIdOrNull(id)
    fun findByEmail(email: String) =
        UserMapper.toDomain(
        userRepository.findByEmail(email)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        )

    fun getSocialEmail(token: String, provider: LoginProvider): String {
        val currentProvider = getProvider(provider)!!
        val accessToken = currentProvider.getAccessToken(token)

        return currentProvider.getUserEmail(accessToken)
    }

    private fun getProvider(provider: LoginProvider) = when (provider) {
        KAKAO -> kakaoSocialLoginApi
        GOOGLE -> TODO()
        NAVER -> TODO()
        LOCAL -> null
    }

    fun existByEmail(email: String) = userRepository.existsByEmail(email)
    fun persist(user: User) {
        val userEntity = UserMapper.toEntity(user)

        userRepository.save(userEntity)
    }

    fun getSocialUserInfo(token: String, provider: LoginProvider): SocialUserInfoDto {
        val currentProvider = getProvider(provider)!!
        return currentProvider.getUserInfo(token)
    }
}