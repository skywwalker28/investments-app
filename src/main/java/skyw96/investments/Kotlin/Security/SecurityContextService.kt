package skyw96.investments.Kotlin.Security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityContextService {
    fun getCurrentUserEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found")

        return authentication.name
    }
}