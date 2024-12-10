package com.mealpick.mealpickServer.global.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode (
    val message: String,
    val customStatus: String,
    val status: HttpStatus,
) {
    // basic
    INTERNAL_ERROR("server error", "B5001", HttpStatus.INTERNAL_SERVER_ERROR),

    // token
    TOKEN_INVALID("access token is invalid", "TK4001", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED("access token is expired", "TK4002", HttpStatus.BAD_REQUEST),

    // user
    USER_NOT_FOUND("user not found", "U2041", HttpStatus.NO_CONTENT)
}