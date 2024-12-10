package com.mealpick.mealpickServer.global.config.security.principal

import com.mealpick.mealpickServer.domain.user.mapper.UserMapper
import com.mealpick.mealpickServer.domain.user.persistence.repository.UserRepository
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*

@Component
class PrincipalDetailService (
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) throw IllegalArgumentException()

        val user = userRepository.findByIdOrNull(UUID.fromString(username))
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val userDomain = UserMapper.toDomain(user)

        return PrincipalDetail(userDomain)
    }
}