package com.mealpick.mealpickServer.domain.user.persistence

import com.mealpick.mealpickServer.domain.user.mapper.UserMapper
import com.mealpick.mealpickServer.domain.user.persistence.repository.UserRepository
import com.mealpick.mealpickServer.global.common.exception.CustomException
import com.mealpick.mealpickServer.global.common.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPersistence (
    private val userRepository: UserRepository
) {

    fun findById(id: UUID) = userRepository.findByIdOrNull(id)
    fun findByEmail(email: String) =
        UserMapper.toDomain(
        userRepository.findByEmail(email)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        )
}