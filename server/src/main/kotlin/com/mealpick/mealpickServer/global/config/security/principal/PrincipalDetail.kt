package com.mealpick.mealpickServer.global.config.security.principal

import com.mealpick.mealpickServer.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetail (
    val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    override fun getPassword(): String = user.password!!

    override fun getUsername(): String = user.name!!
}