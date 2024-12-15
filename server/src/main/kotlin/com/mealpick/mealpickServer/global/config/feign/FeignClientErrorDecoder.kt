package com.mealpick.mealpickServer.global.config.feign

import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import java.io.BufferedReader
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors


class FeignClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response): Exception {
        if (response.status() >= 400) {
            try {
                val body = BufferedReader(response.body().asReader(StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"))
                println(body)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
//            when (response.status()) {
//                401 -> throw CustomException(ErrorCode.FEIGN_UNAUTHORIZED)
//                403 -> throw CustomException(ErrorCode.FEIGN_FORBIDDEN)
//                419 -> throw CustomException(ErrorCode.FEIGN_EXPIRED_TOKEN)
//                else -> throw CustomException(ErrorCode.FEIGN_BAD_REQUEST)
//            }
            throw CustomException(ErrorCode.SOCIAL_API_ERROR)
        }
        return FeignException.errorStatus(methodKey, response)
    }
}