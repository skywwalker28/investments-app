package skyw96.investments.Kotlin.Security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import skyw96.investments.Kotlin.Exception.EmailNotFoundException
import skyw96.investments.Kotlin.Repository.UserRepository


@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String) : UserDetails {

        val user = userRepository.findByEmail(email) ?: throw EmailNotFoundException("Email: $email, not found")
        return User.builder()
            .username(user.email)
            .password(user.password)
            .roles(*user.roles.toTypedArray())
            .build()
    }
}