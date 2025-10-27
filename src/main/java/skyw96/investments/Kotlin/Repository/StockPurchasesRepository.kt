package skyw96.investments.Kotlin.Repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.Kotlin.Model.StockPurchases

@Repository
interface StockPurchasesRepository : JpaRepository<StockPurchases, Long> {
    fun findByUserEmail(email: String): List<StockPurchases>
    fun findByUserEmailAndTicker(email: String, ticker: String): List<StockPurchases>
}