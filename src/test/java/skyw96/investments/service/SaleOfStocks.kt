package skyw96.investments.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import skyw96.investments.Java.AccountsService
import skyw96.investments.Java.InvestmentsService
import skyw96.investments.Kotlin.Exception.InsufficientStocksException
import skyw96.investments.Kotlin.Model.*
import skyw96.investments.Kotlin.Repository.*
import skyw96.investments.Kotlin.Security.SecurityContextService
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class SaleOfStocks {

    @Mock
    private lateinit var stockRepository: StockRepository

    @Mock
    private lateinit var accountsRepository: AccountsRepository

    @Mock
    private lateinit var portfolioRepository: PortfolioRepository

    @Mock
    private lateinit var transactionsRepository: TransactionsRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var accountsService: AccountsService

    @Mock
    private lateinit var securityContextService: SecurityContextService

    @Mock
    private lateinit var stockPurchasesRepository: StockPurchasesRepository

    @InjectMocks
    private lateinit var investmentsService: InvestmentsService

    private lateinit var account: Accounts
    private lateinit var user: User
    private lateinit var portfolio: Portfolio
    private lateinit var stock: Stock

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = User("testName", "testLastname", "test@gmail.com",
            "12345", setOf("USER"))
        account = Accounts(1L, BigDecimal(150), user)
        portfolio = Portfolio(user, "IBM", 3)
        stock = Stock(1L, "IBM", "testDescription",
            BigDecimal(123), BigDecimal(109),12.0, "+10%", BigDecimal(99))
    }

    private val email = "test@gmail.com"
    private val ticker = "IBM"


    @Test
    fun `should correct sale of stock`() {
        val quantity = 2
        val accountId = 1L

        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(portfolioRepository.findByUserEmailAndTicker(email, ticker)).thenReturn(Optional.of(portfolio))
        `when`(stockRepository.findByTicker(ticker)).thenReturn(stock)
        `when`(accountsService.getAccountById(accountId)).thenReturn(account)

        `when`(portfolioRepository.save(any(Portfolio::class.java))).thenAnswer { it.arguments[0] as Portfolio }
        `when`(transactionsRepository.save(any(Transactions::class.java)))
            .thenAnswer { it.arguments[0] as Transactions }
        `when`(accountsRepository.save(any(Accounts::class.java))).thenAnswer { it.arguments[0] as Accounts }

        val result = investmentsService.saleOfStocks(ticker, quantity, accountId)

        assertNotNull(result)
        assertEquals(BigDecimal(396), account.balance)
        assertEquals(1, result.quantity)


        verify(securityContextService).getCurrentUserEmail()
        verify(portfolioRepository).findByUserEmailAndTicker(email, ticker)
        verify(stockRepository).findByTicker(ticker)
        verify(accountsService).getAccountById(accountId)

        verify(portfolioRepository).save(any(Portfolio::class.java))
        verify(transactionsRepository).save(any(Transactions::class.java))
    }

    @Test
    fun `should throw InsufficientStockException`() {
        `when`(securityContextService.getCurrentUserEmail()).thenReturn(email)
        `when`(portfolioRepository.findByUserEmailAndTicker(email, ticker)).thenReturn(Optional.of(portfolio))

        assertThrows<InsufficientStocksException> {
            investmentsService.saleOfStocks(ticker, 5, 1L)
        }

        verify(securityContextService).getCurrentUserEmail()
        verify(portfolioRepository).findByUserEmailAndTicker(email, ticker)

        verify(portfolioRepository, never()).save(any())
        verify(transactionsRepository, never()).save(any())
    }
}