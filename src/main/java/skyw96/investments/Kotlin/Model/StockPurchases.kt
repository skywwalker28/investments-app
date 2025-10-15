package skyw96.investments.Kotlin.Model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "stock_purchases")
data class StockPurchases(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var ticker: String,
    var purchasePrice: BigDecimal,
    var quantity: Int,
    var purchaseDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    var user: User

) {
    constructor() : this(null, "", BigDecimal.ZERO, 0, LocalDateTime.now(), User()) {

    }
}