package com.mealpick.mealpickServer.domain.user.persistence.auth.kakao

import com.mealpick.mealpickServer.domain.user.persistence.auth.SocialLoginApi
import com.mealpick.mealpickServer.domain.user.persistence.auth.SocialUserInfoDto
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class KakaoSocialLoginApi (
    private val kakaoFeignClient: KakaoFeignClient
): SocialLoginApi {
    override fun getAccessToken(token: String) =
        kakaoFeignClient.getAccessToken(token).accessToken

    override fun getUserEmail(accessToken: String): String =
        (kakaoFeignClient.getUserInfo(accessToken).kakaoAccount?.get("email")
            ?: throw CustomException(ErrorCode.SOCIAL_API_ERROR)).toString()

    override fun getUserInfo(token: String): SocialUserInfoDto {
        val kakaoAccount = kakaoFeignClient.getUserInfo(token).kakaoAccount
            ?: throw CustomException(ErrorCode.SOCIAL_API_ERROR)

        val profile = kakaoAccount["profile"] as? Map<*, *>

        return SocialUserInfoDto(
            email = kakaoAccount["email"] as? String ?: throw CustomException(ErrorCode.SOCIAL_API_ERROR),
            ageRange = kakaoAccount["age_range"] as String?,
            gender = kakaoAccount["gender"] as String?,
            nickname = profile?.get("nickname") as String?,
            phone = kakaoAccount["phone_number"] as String?,
        )
    }
}