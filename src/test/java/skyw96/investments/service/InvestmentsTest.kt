package skyw96.investments.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import skyw96.investments.Java.Service.AccountsService
import skyw96.investments.Java.Service.InvestmentsService
import skyw96.investments.Kotlin.Exception.AccountNotFoundException
import skyw96.investments.Kotlin.Exception.InsufficientFundsException
import skyw96.investments.Kotlin.Model.*
import skyw96.investments.Kotlin.Repository.*
import skyw96.investments.Kotlin.Security.SecurityContextService
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class InvestmentsTest {

    @Mock
    private lateinit var stockRepository: StockRepository

    @Mock
    private lateinit var securityContextService: SecurityContextService

    @Mock
    private lateinit var accountsService: AccountsService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var stockPurchasesRepository: StockPurchasesRepository

    @Mock
    private lateinit var transactionsRepository: TransactionsRepository

    @Mock
    private lateinit var accountsRepository: AccountsRepository

    @Mock
    private lateinit var portfolioRepository: PortfolioRepository

    @InjectMocks
    private lateinit var investmentsService: InvestmentsService

    private lateinit var user: User
    private lateinit var account: Accounts
    private lateinit var stock: Stock
    private lateinit var portfolio: Portfolio

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        user = User("testName", "testLastname", "test@gmail.com",
            "12345", setOf("USER"))
        account = Accounts(1L, BigDecimal(1000), user)
        stock = Stock(1L, "IBM", "International Business Machine",
            BigDecimal(150), BigDecimal(123), 12.0, "+10%", BigDecimal(135))
        portfolio = Portfolio(user, "IBM", 2)
    }


    private val ticker = "IBM"
    private val email = "test@gmail.com"
    private val quantity = 5
    private val accountId = 1L
    private val expectedBalance = BigDecimal(250)


    @Test
    fun `should successfully invest when portfolio exist`() {

        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(accountId)).thenReturn(account)
        `when`(userRepository.findByEmail(email)).thenReturn(user)

        `when`(portfolioRepository.existsByUserEmailAndTicker(email, ticker)).thenReturn(true)
        `when`(portfolioRepository.existsByUserEmailAndTicker(email, null)).thenReturn(false)
        `when`(portfolioRepository.findByUserEmailAndTicker(email, ticker)).thenReturn(Optional.of(portfolio))

        `when`(portfolioRepository.save(any(Portfolio::class.java))).thenAnswer{ it.arguments[0] as Portfolio }
        `when`(transactionsRepository.save(any(Transactions::class.java)))
            .thenAnswer{ it.arguments[0] as Transactions }
        `when`(accountsRepository.save(any(Accounts::class.java))).thenAnswer{ it.arguments[0] as Accounts }
        `when`(stockPurchasesRepository.save(any(StockPurchases::class.java)))
            .thenAnswer { it.arguments[0] as StockPurchases }

        val result = investmentsService.investments(ticker, quantity, accountId)

        assertNotNull(result)
        assertEquals(expectedBalance, account.balance)
        assertEquals(7, result.quantity)

        assertEquals(ticker, result.ticker)
        assertEquals(user, result.user)

        verify(securityContextService, times(2)).getCurrentUserEmail()
        verify(userRepository).findByEmail(email)
        verify(stockRepository).findByTicker(ticker)
        verify(accountsService).getAccountById(accountId)
        verify(portfolioRepository).save(any(Portfolio::class.java))
        verify(stockPurchasesRepository).save(any(StockPurchases::class.java))
        verify(transactionsRepository).save(any(Transactions::class.java))
        verify(accountsRepository).save(any(Accounts::class.java))
    }

    @Test
    fun `should successfully invest when portfolio does not exist`() {

        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(accountId)).thenReturn(account)
        `when`(userRepository.findByEmail(email)).thenReturn(user)

        `when`(portfolioRepository.existsByUserEmailAndTicker(email, ticker)).thenReturn(false)
        `when`(portfolioRepository.existsByUserEmailAndTicker(email, null)).thenReturn(false)
        `when`(portfolioRepository.findByUserEmailAndTicker(email, ticker)).thenReturn(Optional.of(portfolio))

        `when`(portfolioRepository.save(any(Portfolio::class.java))).thenAnswer { it.arguments[0] as Portfolio }
        `when`(transactionsRepository.save(any(Transactions::class.java)))
            .thenAnswer { it.arguments[0] as Transactions }
        `when`(accountsRepository.save(any(Accounts::class.java))).thenAnswer { it.arguments[0] as Accounts }
        `when`(stockPurchasesRepository.save(any(StockPurchases::class.java)))
            .thenAnswer { it.arguments[0] as StockPurchases }

        val result = investmentsService.investments(ticker, quantity, accountId)

        assertNotNull(result)
        assertEquals(expectedBalance, account.balance)
        assertEquals(5, result.quantity)
        assertEquals(ticker, result.ticker)
        assertEquals(user, result.user)

        verify(portfolioRepository).save(any(Portfolio::class.java))
        verify(transactionsRepository).save(any(Transactions::class.java))
        verify(accountsRepository).save(any(Accounts::class.java))
        verify(stockPurchasesRepository).save(any(StockPurchases::class.java))
    }

    @Test
    fun `should update portfolio with null ticker to actual ticker`() {
        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(1L)).thenReturn(account)
        `when`(userRepository.findByEmail(email)).thenReturn(user)

        `when`(portfolioRepository.existsByUserEmailAndTicker(email, ticker)).thenReturn(false)
        `when`(portfolioRepository.existsByUserEmailAndTicker(email, null)).thenReturn(true)
        `when`(portfolioRepository.findByUserEmailAndTicker(email, null)).thenReturn(Optional.of(portfolio))
        `when`(portfolioRepository.findByUserEmailAndTicker(email, ticker)).thenReturn(Optional.of(portfolio))

        `when`(portfolioRepository.save(any(Portfolio::class.java))).thenAnswer { it.arguments[0] as Portfolio }
        `when`(stockPurchasesRepository.save(any(StockPurchases::class.java)))
            .thenAnswer { it.arguments[0] as StockPurchases }
        `when`(transactionsRepository.save(any(Transactions::class.java)))
            .thenAnswer { it.arguments[0] as Transactions }
        `when`(accountsRepository.save(any(Accounts::class.java))).thenAnswer { it.arguments[0] as Accounts }

        val result = investmentsService.investments(ticker, quantity, accountId)

        assertNotNull(result)
        assertEquals(ticker, result.ticker)
        assertEquals(expectedBalance, account.balance)
        assertEquals(user, result.user)

        verify(portfolioRepository).save(any(Portfolio::class.java))
        verify(transactionsRepository).save(any(Transactions::class.java))
        verify(accountsRepository).save(any(Accounts::class.java))
        verify(stockPurchasesRepository).save(any(StockPurchases::class.java))
    }

    @Test
    fun `should throw InsufficientFundsException when not enough balance`() {
        val poorAccount = Accounts(1L, BigDecimal(100), user)

        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(1L)).thenReturn(poorAccount)
        `when`(userRepository.findByEmail(email)).thenReturn(user)

        val exception = assertThrows<InsufficientFundsException> {
            investmentsService.investments(ticker, quantity, accountId)
        }

        assert(exception.message!!.contains("Not enough money on account"))
        verify(stockPurchasesRepository, never()).save(any())
        verify(portfolioRepository, never()).save(any())
        verify(transactionsRepository, never()).save(any())
        verify(accountsRepository, never()).save(any())
    }

    @Test
    fun `should throw AccountNotFound when account does match to user`() {
        val errorEmail = "test2@gmail.com"

        `when`(securityContextService.getCurrentUserEmail()).thenReturn(errorEmail)
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(1L)).thenReturn(account)

        val exception = assertThrows<AccountNotFoundException> {
            investmentsService.investments(ticker, quantity, accountId)
        }

        assert(exception.message!!.contains("Account does not belong to current user"))

        verify(accountsRepository, never()).save(any())
        verify(portfolioRepository, never()).save(any())
        verify(transactionsRepository, never()).save(any())
        verify(stockPurchasesRepository, never()).save(any())
    }
}