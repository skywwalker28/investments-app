package skyw96.investments.Kotlin.Repository

import org.springframework.data.jpa.repository.JpaRepository
import skyw96.investments.Kotlin.Model.Transactions

interface TransactionsRepository : JpaRepository<Transactions, Long> {
    fun findByAccountId(accountId: Long) : List<Transactions>
}