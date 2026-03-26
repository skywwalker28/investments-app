package skyw96.investments.kotlin.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import skyw96.investments.kotlin.repository.UserRepository


@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String) : UserDetails {

        val user = userRepository.findByEmail(email)
        return User.builder()
            .username(user.email)
            .password(user.password)
            .roles(*user.roles.toTypedArray())
            .build()
    }
}