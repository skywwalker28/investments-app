package skyw96.investments.Kotlin.DTO

import java.time.LocalDateTime

data class UserDTO(
    val id: Long?,
    val email: String,
    val firstname: String,
    val lastname: String,
    val createdAt: LocalDateTime,
    val accountIds: List<Long?>
) {
    companion object {
        fun fromEntity(user: skyw96.investments.Kotlin.Model.User) : UserDTO {
            return UserDTO(
                id = user.id,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                createdAt = user.createdAt,
                accountIds = user.list.map { it.id }
            )
        }
    }
}