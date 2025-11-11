package skyw96.investments.Java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyw96.investments.Kotlin.Exception.AccountNotFoundException;
import skyw96.investments.Kotlin.Model.Accounts;
import skyw96.investments.Kotlin.Model.Transactions;
import skyw96.investments.Kotlin.Model.User;
import skyw96.investments.Kotlin.Repository.AccountsRepository;
import skyw96.investments.Kotlin.Repository.TransactionsRepository;
import skyw96.investments.Kotlin.Security.SecurityContextService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    SecurityContextService securityContextService;

    public List<Accounts> viewAccounts(String email) {
        return accountsRepository.findByUserEmail(email);
    }

    public Accounts createAccount(User user) {
        Accounts account = new Accounts(user);
        return accountsRepository.save(account);
    }

    public Accounts getAccountById(Long id) {
        return accountsRepository.findById(id).orElseThrow(() ->
                new AccountNotFoundException("Account with id " + id + " not found"));
    }

    public Accounts deposit(Long id, BigDecimal amount) {
        Accounts account = getAccountById(id);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);

        account.setBalance(newBalance);

        String description = "The user with email \"" +
                securityContextService.getCurrentUserEmail() + "\" deposited the amount. " +
                "The amount has been deposited to the account: " + account.getId();

        Transactions transactions = new Transactions("+" + amount.toString() + "₽",
                "DEPOSIT", description, account);
        transactionsRepository.save(transactions);

        return accountsRepository.save(account);
    }
}
