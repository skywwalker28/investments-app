package skyw96.investments.Kotlin.Model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "Transactions")
data class Transactions (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var amount: String,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var transactionDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Accounts,

){
    constructor(amount: String, type: String, description: String, account: Accounts) :
            this(null, amount, type, description, LocalDateTime.now(), account)
    constructor() : this (null, "", "", "", LocalDateTime.now(), Accounts())
}