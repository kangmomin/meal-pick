package com.mealpick.mealpickServer.global.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import com.mealpick.mealpickServer.global.common.response.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityExceptionHandler (): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var responseValue: String? = null
        try {
            doFilter(request, response, filterChain)
        } catch (e: CustomException) {
            response.status = e.errorCode.status.ordinal

            responseValue = ObjectMapper()
                .writeValueAsString(
                    ErrorResponse(e.errorCode.customStatus, e.errorCode.message))
        } catch (e: Exception) {
            val errorCode = ErrorCode.INTERNAL_ERROR
            response.status = errorCode.status.ordinal

            responseValue = ObjectMapper()
                .writeValueAsString(
                    ErrorResponse(errorCode.customStatus, errorCode.message)
                )
        } finally {
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"

            response.writer.write(responseValue!!)
        }
    }
}