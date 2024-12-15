package com.mealpick.mealpickServer.domain.user.persistence.auth.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoResponse(
    val id: Long,
    @field: JsonProperty("connected_at")
    val connectedAt: String?,
    val properties: Map<String, String>?,
    @field: JsonProperty("kakao_account")
    val kakaoAccount: Map<String, Any>?
)