package com.baum.baas.local.entity.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


/*
* 사용하지않음. 공부용으로 남겨둠.
*
* */
class CustomUserDetails(val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return user.roles
            .map { it.role }
            .map { role -> SimpleGrantedAuthority("ROLE_$role") }
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}