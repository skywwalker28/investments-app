package skyw96.investments.kotlin.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountsDTO(

    val id: Long?,
    val balance: BigDecimal,
    val balanceWithCurrency: String,
    val createdAt: LocalDateTime,
    val userId: Long?
) {
    companion object {
        fun fromEntity(account: skyw96.investments.kotlin.model.Accounts) : AccountsDTO {
            return AccountsDTO(
                id = account.id,
                balance = account.balance,
                balanceWithCurrency = "${account.balance}₽",
                createdAt = account.createdAt,
                userId = account.user.id
            )
        }
    }
}