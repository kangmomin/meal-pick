package com.mealpick.mealpickServer.global.config.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Base64.Decoder

@Component
class JwtProperty (
    @Value("\${jwt.access.key}")
    private val accessKeyVal: String,
    @Value("\${jwt.access.exp}")
    val accessExp: Long,
    @Value("\${jwt.refresh.key}")
    private val refreshKeyVal: String,
    @Value("\${jwt.refresh.exp")
    val refreshExp: Long
) {

    val accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKeyVal))
    val refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKeyVal))
}