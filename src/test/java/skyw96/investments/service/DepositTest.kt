package skyw96.investments.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import skyw96.investments.Java.AccountsService
import skyw96.investments.Kotlin.Model.Accounts
import skyw96.investments.Kotlin.Model.Transactions
import skyw96.investments.Kotlin.Model.User
import skyw96.investments.Kotlin.Repository.AccountsRepository
import skyw96.investments.Kotlin.Repository.TransactionsRepository
import skyw96.investments.Kotlin.Security.SecurityContextService
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class DepositTest {
    @Mock
    private lateinit var accountsRepository: AccountsRepository

    @Mock
    private lateinit var transactionsRepository: TransactionsRepository

    @Mock
    private lateinit var securityContextService: SecurityContextService

    @InjectMocks
    private lateinit var accountsService: AccountsService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should add the money in account`() {
        val user = User("testName", "testLastname", "test@gmail.com",
            "12345", setOf("USER"))

        val balance = BigDecimal(100)
        val amount = BigDecimal(150)
        val expectedBalance = BigDecimal(250)

        val account = Accounts(1L, balance, user)

        `when`(accountsRepository.findById(1L)).thenReturn(Optional.of(account))
        `when`(securityContextService.getCurrentUserEmail()).thenReturn("test@gmail.com")
        `when`(accountsRepository.save((any(Accounts::class.java)))).thenAnswer { it.arguments[0] as Accounts }
        `when`(transactionsRepository.save((any(Transactions::class.java)))).thenAnswer {
        it.arguments[0] as Transactions }

        val result = accountsService.deposit(1L, amount)

        assertEquals(expectedBalance, result.balance)
        verify(accountsRepository).findById(1L)
        verify(accountsRepository).save(any(Accounts::class.java))
        verify(transactionsRepository).save(any(Transactions::class.java))
    }
}