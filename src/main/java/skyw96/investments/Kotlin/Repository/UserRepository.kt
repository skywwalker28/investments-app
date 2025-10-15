package skyw96.investments.Kotlin.Repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.Kotlin.Model.User

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findByEmail(email: String): User

    fun existsByEmail(email: String): Boolean
}