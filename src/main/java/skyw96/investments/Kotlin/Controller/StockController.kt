package skyw96.investments.Kotlin.Controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import skyw96.investments.Java.Service.StockService
import skyw96.investments.Kotlin.DTO.StockDTO
import java.math.BigDecimal

@RestController
@RequestMapping("/api")
open class StockController(
    private val stockService: StockService
) {


    @GetMapping("/stock/viewAllStocks")
    open fun viewAllStocks() : List<StockDTO> {
        return stockService.viewAllDTO()
    }

    @PostMapping("/stock/createStock")
    open fun createStock(@RequestParam ticker: String,
                    @RequestParam companyName: String,
                    @RequestParam currentPrice: BigDecimal,
                    @RequestParam previousClose: BigDecimal,
                    @RequestParam volatility: Double,
                    @RequestParam originalPrice: BigDecimal
                    ) : StockDTO {
        val stock = stockService.createStock(ticker, companyName, currentPrice,
            previousClose, volatility, originalPrice)
        return StockDTO.fromEntity(stock)
    }

    @PostMapping("/{ticker}/setStock")
    open fun updateStock(@PathVariable ticker: String, @RequestParam newPrice: BigDecimal,
                    @RequestParam newTicker: String, @RequestParam newVolatility: Double,
                    @RequestParam newProfitability: String, @RequestParam newOriginalPrice: BigDecimal,
                         @RequestParam newCompanyName: String) : StockDTO {

        val stock = stockService.updateStock(ticker, newPrice, newTicker,
            newVolatility, newProfitability, newOriginalPrice, newCompanyName)

        return StockDTO.fromEntity(stock)
    }
}