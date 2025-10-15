package skyw96.investments.Kotlin.Model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
@Table(name = "User_accounts")
data class Accounts (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var balance: BigDecimal,
    var createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    var user: User
) {
    constructor(user: User) : this(null, BigDecimal.ZERO, LocalDateTime.now(), user)

    constructor() : this(null, BigDecimal.ZERO, LocalDateTime.now(), User())
}