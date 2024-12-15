package com.mealpick.mealpickServer.domain.user.persistence.auth

interface SocialLoginApi {
    fun getAccessToken(token: String): String

    /** @return email */
    fun getUserEmail(accessToken: String): String

    fun getUserInfo(accessToken: String): SocialUserInfoDto
}