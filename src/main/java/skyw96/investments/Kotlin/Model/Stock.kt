package skyw96.investments.Kotlin.Model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
@Table(name = "Stocks")
data class Stock (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var ticker: String,

    @Column(nullable = false)
    var companyName: String,

    @Column(nullable = false, precision = 10, scale = 2)
    var currentPrice: BigDecimal,

    @Column(precision = 10, scale = 2)
    var previousClose: BigDecimal,

    @Column(nullable = false)
    var volatility: Double = 0.02,

    @Column
    var profitability: String="",

    @Column
    var originalPrice: BigDecimal = BigDecimal.ZERO,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()

) {
    constructor() : this(null, "", "", BigDecimal.ZERO, BigDecimal.ZERO,
        0.02, "0%", BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now())

    constructor(ticker: String,
            companyName: String,
            currentPrice: BigDecimal,
            previousClose: BigDecimal,
            volatility: Double,
            profitability: String,
            originalPrice: BigDecimal
            ) : this (null, ticker, companyName, currentPrice, previousClose, volatility,
        profitability, originalPrice)
}
