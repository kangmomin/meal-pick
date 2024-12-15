package com.mealpick.mealpickServer.domain.user.controller.request

import com.mealpick.mealpickServer.domain.user.model.constant.LoginProvider
import jakarta.validation.constraints.NotNull

data class JoinRequest (
    val email: String?,
    val password: String?,

    @field: NotNull(message = "로그인 방식 값이 비었습니다.")
    val provider: LoginProvider?,

    val token: String?
)