package com.mealpick.mealpickServer.global.config.security.jwt

import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.config.security.jwt.dto.TokenDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtUtil (
    private val jwtProperty: JwtProperty
) {
    /**
     * @exception invalidTokenException 토큰이 없다
     */
    fun isExpired(token: String): Boolean =
        parsesAccess(token)?.expiration?.after(Date()) ?:
        throw IllegalArgumentException("token 이 없음")

    fun parsesAccess(token: String): Claims? =
        Jwts.parserBuilder()
            .setSigningKey(jwtProperty.accessKey)
            .build()
            .parseClaimsJwt(token)
            .body

    fun parseRefresh(token: String): Claims? =
        Jwts.parserBuilder()
            .setSigningKey(jwtProperty.refreshKey)
            .build()
            .parseClaimsJws(token)
            .body

    fun generate(userId: UUID): TokenDto {
        val accessToken = generateToken(
            jwtProperty.accessKey,
            jwtProperty.accessExp,
            userId.toString(),
            null)

        val refreshToken = generateToken(
            jwtProperty.refreshKey,
            jwtProperty.refreshExp,
            userId.toString(),
            null)

        return TokenDto(accessToken, refreshToken)
    }

    private fun generateToken(key: SecretKey, exp: Long, subject: String, claim: Claims?) =
        Jwts.builder()
            .signWith(key)
            .setIssuedAt(Date())
            .setSubject(subject)
            .setClaims(claim)
            .setExpiration(Date(System.currentTimeMillis() + exp))
            .compact()
}