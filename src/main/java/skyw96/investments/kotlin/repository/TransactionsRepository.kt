package skyw96.investments.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import skyw96.investments.kotlin.model.Transactions

interface TransactionsRepository : JpaRepository<Transactions, Long> {
    fun findByAccountId(accountId: Long) : List<Transactions>
}