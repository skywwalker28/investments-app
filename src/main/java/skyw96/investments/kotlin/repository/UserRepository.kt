package skyw96.investments.kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import skyw96.investments.kotlin.model.User

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findByEmail(email: String): User

    fun existsByEmail(email: String): Boolean
}