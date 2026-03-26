package skyw96.investments.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import skyw96.investments.java.AccountsService
import skyw96.investments.kotlin.model.Accounts
import skyw96.investments.kotlin.model.User

import skyw96.investments.kotlin.repository.AccountsRepository
import skyw96.investments.kotlin.repository.TransactionsRepository

class CreateAccount {

    @Mock
    private lateinit var accountRepository: AccountsRepository

    @Mock
    private lateinit var transactionsRepository: TransactionsRepository

    @InjectMocks
    private lateinit var accountsService: AccountsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create account`() {
        val user = User("testName", "testLastname",
            "test@gmail.com", "1234", setOf("USER"))

        accountsService.createAccount(user)

        verify(accountRepository).save((any(Accounts::class.java)))
    }
}