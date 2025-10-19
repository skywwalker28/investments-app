package skyw96.investments.Kotlin.Controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import skyw96.investments.Java.Service.AccountsService
import skyw96.investments.Kotlin.DTO.AccountsDTO
import skyw96.investments.Kotlin.Repository.UserRepository
import java.math.BigDecimal


@Tag(name = "Account_methods")
@RestController
@RequestMapping("/api")
class AccountController (
    private val userRepository: UserRepository,
    private val accountsService: AccountsService
) {

    @GetMapping("/accounts/view")
    fun viewAccount() : List<AccountsDTO>{
        val accounts = accountsService.viewAccounts(accountsService.currentUserEmail)
        return accounts.map { AccountsDTO.fromEntity(it) }
    }


    @PostMapping("/accounts/create")
    fun createAccount() : AccountsDTO {

        val user = userRepository.findByEmail(accountsService.currentUserEmail)
        val createUser = accountsService.createAccount(user)

        return AccountsDTO.fromEntity(createUser)
    }


    @PostMapping("/accounts/deposit")
    fun deposit(@RequestParam id: Long, @RequestParam amount: BigDecimal) : AccountsDTO {
        val account = accountsService.deposit(id, amount)
        return AccountsDTO.fromEntity(account)
    }
}