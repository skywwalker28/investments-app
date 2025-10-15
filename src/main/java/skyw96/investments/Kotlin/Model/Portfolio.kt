package skyw96.investments.Kotlin.Model

import jakarta.persistence.*

@Entity
@Table(name = "Portfolio")
class Portfolio (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    var user: User,

    var ticker: String? = null,

    @Column(nullable = false)
    var quantity: Int,

    ){

    constructor() : this(null, User(), null, 0)
    constructor(user: User, ticker: String?, quantity: Int) : this(null, user, ticker, quantity)
}
