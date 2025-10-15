package skyw96.investments.Kotlin.DTO

import skyw96.investments.Kotlin.Model.Portfolio

data class PortfolioDTO (
    var ticker: String?,
    var quantity: Int
){
    companion object {
        fun fromEntity(portfolio: Portfolio) : PortfolioDTO {
            return PortfolioDTO(
                ticker = portfolio.ticker,
                quantity = portfolio.quantity,
            )
        }
    }
}