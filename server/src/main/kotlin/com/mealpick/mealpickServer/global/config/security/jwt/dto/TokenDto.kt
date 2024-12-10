package com.mealpick.mealpickServer.global.config.security.jwt.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)
