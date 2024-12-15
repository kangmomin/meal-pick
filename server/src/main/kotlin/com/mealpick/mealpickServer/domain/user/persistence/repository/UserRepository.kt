package com.mealpick.mealpickServer.domain.user.persistence.repository

import com.mealpick.mealpickServer.domain.user.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {

    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}