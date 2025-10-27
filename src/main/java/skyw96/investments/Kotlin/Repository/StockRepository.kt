package skyw96.investments.Kotlin.Repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.Kotlin.Model.Stock

@Repository
interface StockRepository : JpaRepository<Stock, Long> {
    fun findByTicker(ticker: String): Stock?
    fun existsByTicker(ticker: String): Boolean
}