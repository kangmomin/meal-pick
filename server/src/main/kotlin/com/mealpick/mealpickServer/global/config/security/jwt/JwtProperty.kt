package com.mealpick.mealpickServer.global.config.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtProperty (
    @Value("\${jwt.access.key}")
    private val accessKeyVal: String,
    @Value("\${jwt.access.exp}")
    val accessExp: Long,
    @Value("\${jwt.refresh.key}")
    private val refreshKeyVal: String,
    @Value("\${jwt.refresh.exp}")
    val refreshExp: Long
) {

    val accessKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKeyVal))
    val refreshKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKeyVal))
}