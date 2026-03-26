package skyw96.investments.kotlin.dto

import skyw96.investments.kotlin.model.Stock
import java.math.BigDecimal

data class StockDTO (

    val ticker: String,
    val companyName: String,
    val currentPrice: BigDecimal,
    val previousClose: BigDecimal,
    val volatility: Double,
    val profitability: String
) {
    companion object {
        fun fromEntity(stock: Stock) : StockDTO {
            return StockDTO(
                ticker = stock.ticker,
                companyName = stock.companyName,
                currentPrice = stock.currentPrice,
                previousClose = stock.previousClose,
                volatility = stock.volatility,
                profitability = stock.profitability
            )
        }
    }
}
