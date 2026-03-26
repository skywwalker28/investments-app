package skyw96.investments.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.kotlin.model.StockPurchases

@Repository
interface StockPurchasesRepository : JpaRepository<StockPurchases, Long> {
    fun findByUserEmail(email: String): List<StockPurchases>
    fun findByUserEmailAndTicker(email: String, ticker: String): List<StockPurchases>
}