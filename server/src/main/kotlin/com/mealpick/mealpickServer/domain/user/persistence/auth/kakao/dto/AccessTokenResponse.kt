package com.mealpick.mealpickServer.domain.user.persistence.auth.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @field: JsonProperty("access_token")
    val accessToken: String,

    @field: JsonProperty("token_type")
    val tokenType: String,

    @field: JsonProperty("expires_in")
    val expiresIn: Int,

    @field: JsonProperty("refresh_token")
    val refreshToken: String?,

    val scope: String?
)