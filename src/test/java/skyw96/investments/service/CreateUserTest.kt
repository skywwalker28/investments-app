package skyw96.investments.service

import org.junit.jupiter.api.*
import org.mockito.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.security.crypto.password.PasswordEncoder
import skyw96.investments.Java.UserService
import skyw96.investments.Kotlin.Exception.EmailAlreadyExistException
import skyw96.investments.Kotlin.Model.Portfolio
import skyw96.investments.Kotlin.Model.User
import skyw96.investments.Kotlin.Repository.*
import kotlin.test.assertEquals

class CreateUserTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var portfolioRepository: PortfolioRepository

    @InjectMocks
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should register user with encode password`() {
        val firstname = "testName"
        val lastname = "testLastname"
        val email = "test@gmail.com"
        val password = "testPassword"
        val encodedPassword = "encodedPassword"

        val expectedUser = User(firstname, lastname, email, encodedPassword, setOf("USER"))
        val expectedPortfolio = Portfolio(null, expectedUser, null, 0)

        `when`(passwordEncoder.encode(password)).thenReturn(encodedPassword)
        `when`(userRepository.existsByEmail(email)).thenReturn(false)
        `when`(userRepository.save(any(User::class.java))).thenReturn(expectedUser)
        `when`(portfolioRepository.save(any(Portfolio::class.java))).thenReturn(expectedPortfolio)

        val result = userService.registerUser(firstname, lastname, email, password)

        assertEquals(expectedUser, result)
        verify(passwordEncoder).encode(password)
        verify(userRepository).existsByEmail(email)
        verify(userRepository).save(any(User::class.java))
        verify(portfolioRepository).save(any(Portfolio::class.java))
    }

    @Test
    fun `should throw exception when email already exists`() {
        val firstname = "testName"
        val lastname = "testLastname"
        val email = "test@gmail.com"
        val password = "testPassword"

        `when`(userRepository.existsByEmail(email)).thenReturn(true)

        assertThrows<EmailAlreadyExistException> {
            userService.registerUser(firstname, lastname, email, password)
        }

        verify(userRepository).existsByEmail(email)
        verify(userRepository, never()).save(any(User::class.java))
        verify(portfolioRepository, never()).save(any(Portfolio::class.java))
    }
}