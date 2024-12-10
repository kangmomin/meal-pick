package com.mealpick.mealpickServer.global.config.security.filter

import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import com.mealpick.mealpickServer.global.config.security.jwt.JwtUtil
import com.mealpick.mealpickServer.global.config.security.principal.PrincipalDetailService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.IllegalArgumentException

@Component
class JwtAuthFilter (
    private val jwtUtil: JwtUtil,
    private val principalDetailService: PrincipalDetailService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authentication")
        if (token.isNotBlank()) {
            if (jwtUtil.isExpired(token)) throw CustomException(ErrorCode.TOKEN_EXPIRED)
            val userId = jwtUtil.parsesAccess(token)?.subject
            try {
                if (userId.isNullOrBlank()) throw CustomException(ErrorCode.TOKEN_INVALID)
                val principal = principalDetailService.loadUserByUsername(userId)

                val context = SecurityContextHolder.getContext()
                context.authentication = UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
            } catch (e: IllegalArgumentException) {
                throw CustomException(ErrorCode.TOKEN_INVALID)
            }
        }

        filterChain.doFilter(request, response)
    }
}