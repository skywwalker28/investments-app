package skyw96.investments.Kotlin.Controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import skyw96.investments.Java.UserService
import skyw96.investments.Kotlin.DTO.UserDTO
import skyw96.investments.Kotlin.Exception.EmailNotFoundException
import skyw96.investments.Kotlin.Repository.UserRepository
import skyw96.investments.Kotlin.Security.JwtTokenProvider


@Tag(name = "Auth_methods")
@RestController
@RequestMapping("/api")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    data class RegisterRequest(val email: String, val password: String, val firstname: String, val lastname: String)
    data class LoginRequest(val email: String, val password: String)
    data class AuthResponse(val token: String, val user: UserDTO)



    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterRequest): AuthResponse {
        val user = userService.registerUser(
            request.firstname,
            request.lastname,
            request.email,
            request.password
        )

        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            request.email, request.password))
        SecurityContextHolder.getContext().authentication = authentication

        val token = jwtTokenProvider.generateToken(authentication.principal as UserDetails)
        return AuthResponse(token, UserDTO.fromEntity(user))
    }


    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse {
        val authentication: Authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            request.email, request.password))
        SecurityContextHolder.getContext().authentication = authentication

        val token = jwtTokenProvider.generateToken(authentication.principal as UserDetails)
        val user = userRepository.findByEmail(request.email) ?: throw EmailNotFoundException("User with email: " +
                request.email + " not found")

        return AuthResponse(token, UserDTO.fromEntity(user))
    }
}