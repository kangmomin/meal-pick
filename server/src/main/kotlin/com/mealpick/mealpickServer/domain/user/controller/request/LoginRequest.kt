package com.mealpick.mealpickServer.domain.user.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class LoginRequest (
    @field: NotBlank(message = "이메일이 비었습니다.")
    @field: Email(message = "이메일 형식이 맞지 않습니다.")
    val email: String,

    @field: NotBlank(message = "비밀번호가 비었습니다.")
    @field: Size(min = 8, max = 30, message = "비밀번호는 최소 8자에서 30자 이하여야 합니다.")
    val password: String
)