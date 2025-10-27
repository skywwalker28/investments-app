package skyw96.investments.Kotlin.Controller

import org.springframework.web.bind.annotation.*
import skyw96.investments.Java.Service.AccountsService
import skyw96.investments.Java.Service.InvestmentsService
import skyw96.investments.Kotlin.DTO.PortfolioDTO
import skyw96.investments.Kotlin.DTO.TransactionsDTO

@RestController
@RequestMapping("/api")
class InvestmentsController (
    private val transactionsService: InvestmentsService,
    private val accountsService: AccountsService
){

    @GetMapping("/investments/viewMyStocks")
    fun viewMyStocks() : List<PortfolioDTO> {
        val accounts = transactionsService.viewMyStocks(accountsService.currentUserEmail)
        return accounts.map { PortfolioDTO.fromEntity(it) }
    }

    @GetMapping("/investments/viewMyHistory")
    fun viewMyHistory(@RequestParam accountId: Long) : List<TransactionsDTO> {
        val history = transactionsService.viewMyHistory(accountId)
        return history.map { TransactionsDTO.fromEntity(it) }
    }

    @PostMapping("/investments")
    fun investments(@RequestParam ticker: String,
                    @RequestParam accountsId: Long,
                    @RequestParam quantity: Int) : PortfolioDTO {
        val portfolio = transactionsService.investments(ticker, quantity, accountsId)
        return PortfolioDTO.fromEntity(portfolio)
    }

    @PostMapping("/investments/sell")
    fun sellOfStocks(@RequestParam ticker: String,
                     @RequestParam quantity: Int,
                     @RequestParam accountsId: Long) : PortfolioDTO {
        val portfolio = transactionsService.saleOfStocks(ticker, quantity, accountsId)
        return PortfolioDTO.fromEntity(portfolio)
    }
}