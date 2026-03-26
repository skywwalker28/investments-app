package skyw96.investments.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.kotlin.model.Accounts

@Repository
interface AccountsRepository : JpaRepository<Accounts, Long> {
    fun findByUserEmail(email: String) : List<Accounts>?
}