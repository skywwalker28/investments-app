package skyw96.investments.kotlin.dto

import skyw96.investments.kotlin.model.Portfolio

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