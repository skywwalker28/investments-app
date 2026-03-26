package skyw96.investments.kotlin.dto

import skyw96.investments.kotlin.model.Transactions
import java.time.LocalDateTime

data class TransactionsDTO(
    var amount: String,
    var type: String,
    var transactionDate: LocalDateTime,
    var accountId: Long?

) {
    companion object{
        fun fromEntity(transactions: Transactions) : TransactionsDTO {
            return TransactionsDTO(
                amount = transactions.amount,
                type = transactions.type,
                transactionDate = transactions.transactionDate,
                accountId = transactions.id
            )
        }
    }
}