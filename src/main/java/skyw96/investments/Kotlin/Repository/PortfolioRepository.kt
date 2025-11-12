package skyw96.investments.Kotlin.Repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.Kotlin.Model.Portfolio
import java.util.*

@Repository
interface PortfolioRepository : JpaRepository<Portfolio, Long> {
    fun findByUserEmail(email: String): List<Portfolio>
    fun findByUserEmailAndTicker(email: String, ticker: String?): Optional<Portfolio>

    fun existsByUserEmailAndTicker(email: String, ticker: String?): Boolean
}