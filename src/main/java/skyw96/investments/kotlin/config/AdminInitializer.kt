package skyw96.investments.kotlin.config

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import skyw96.investments.java.UserService
import skyw96.investments.kotlin.repository.UserRepository

@Configuration
open class AdminInitializer {

    @Bean
    open fun initAdmin(userService: UserService, userRepository: UserRepository) : ApplicationRunner {
        return ApplicationRunner {
            val adminMail = "admin@gmail.com"

            if (!userRepository.existsByEmail(adminMail)) {
                userService.createAdmin("Admin", "User", adminMail, "admin123")
                println("Admin user created: $adminMail / admin 123")
            } else {
                println("Admin user already exists: $adminMail")
            }
        }
    }
}