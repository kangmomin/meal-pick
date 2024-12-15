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
    USER_NOT_FOUND("user not found", "U2041", HttpStatus.NO_CONTENT),
    USER_CONFLICT("user email is already registed", "U4091", HttpStatus.CONFLICT),

    USER_EMAIL_NOT_VALID("user email is not valid", "UV4001", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_NOT_VALID("user password is not valid", "UV4002", HttpStatus.BAD_REQUEST),
    USER_OAUTH_TOKEN_NOT_VALID("user oauth token it not valid", "UV4003", HttpStatus.BAD_REQUEST),

    SOCIAL_API_ERROR("social login api error", "US5001", HttpStatus.INTERNAL_SERVER_ERROR),
}