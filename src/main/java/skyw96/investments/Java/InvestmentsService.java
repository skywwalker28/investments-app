package skyw96.investments.Java;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyw96.investments.Kotlin.Exception.*;
import skyw96.investments.Kotlin.Model.*;
import skyw96.investments.Kotlin.Repository.*;
import skyw96.investments.Kotlin.Security.SecurityContextService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class InvestmentsService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountsService accountsService;

    @Autowired
    SecurityContextService securityContextService;

    @Autowired
    StockPurchasesRepository stockPurchasesRepository;


    public List<Portfolio> viewMyStocks(String email) {
        return portfolioRepository.findByUserEmail(email);
    }

    public List<Transactions> viewMyHistory(Long accountId) {
        return transactionsRepository.findByAccountId(accountId);
    }

    @Transactional
    public Portfolio investments(String ticker, int quantity, Long accountId) {
        Stock stock = stockRepository.findByTicker(ticker);
        String email = securityContextService.getCurrentUserEmail();

        Accounts account = accountsService.getAccountById(accountId);

        if (stock == null) {
            throw new StockNotFoundException("Stock with ticker: " + ticker + ", not found");
        }
        if (!account.getUser().getEmail().equals(securityContextService.getCurrentUserEmail())) {
            throw new AccountNotFoundException("Account does not belong to current user");
        }

        BigDecimal priceForBuy = stock.getCurrentPrice();
        BigDecimal amount = priceForBuy.multiply(BigDecimal.valueOf(quantity));



        String description = "The user with email \"" + email + "\" bought " + quantity + " stocks of " + ticker +
                ". The amount was withdrawn from the account: " + accountId;


        Transactions transactions = new Transactions(amount.negate().toString() + "₽",
                "BUY STOCK", description, account);
        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough money on account. Current balance: " + currentBalance +
                    ", required: " + amount);
        }

        StockPurchases stockPurchases = new StockPurchases();
        stockPurchases.setTicker(ticker);
        stockPurchases.setQuantity(quantity);
        stockPurchases.setPurchasePrice(priceForBuy);
        stockPurchases.setUser(userRepository.findByEmail(email));
        stockPurchasesRepository.save(stockPurchases);


        BigDecimal newBalance = currentBalance.subtract(amount);

        account.setBalance(newBalance);
        accountsRepository.save(account);
        Portfolio portfolio;

        if (!portfolioRepository.existsByUserEmailAndTicker(email, ticker) &&
                !portfolioRepository.existsByUserEmailAndTicker(email, null)) {
           portfolio = new Portfolio(userRepository.findByEmail(email), ticker, quantity);

           transactionsRepository.save(transactions);
           return portfolioRepository.save(portfolio);
        } else if (portfolioRepository.existsByUserEmailAndTicker(email, null)) {
               portfolio = portfolioRepository.findByUserEmailAndTicker(email, null).orElseThrow(() ->
                    new StockNotFoundException("Stock with ticker: " + ticker + ", not found"));

               portfolio.setTicker(ticker);
        }

        portfolio = portfolioRepository.findByUserEmailAndTicker(email, ticker).orElseThrow(() ->
                new StockNotFoundException("Stock with ticker: " + ticker + ", not found"));
        portfolio.setQuantity(portfolio.getQuantity() + quantity);

        transactionsRepository.save(transactions);
        return portfolioRepository.save(portfolio);
    }

    @Transactional
    public Portfolio saleOfStocks(String ticker, int quantity, Long accountId) {

        String email = securityContextService.getCurrentUserEmail();
        Portfolio portfolio = portfolioRepository.findByUserEmailAndTicker(email, ticker).orElseThrow(() ->
                new StockNotFoundException("Stock with ticker: " + ticker + ", not found"));
        if (portfolio.getQuantity() - quantity < 0) {
            throw new InsufficientStocksException("Not enough stocks on portfolio. Current available stocks: " +
                    portfolio.getQuantity() + ", required: " + quantity);
        }

        Stock stock = stockRepository.findByTicker(ticker);
        portfolio.setQuantity(portfolio.getQuantity() - quantity);


        Accounts account = accountsService.getAccountById(accountId);

        BigDecimal amount = account.getBalance().add(stock.getCurrentPrice().multiply(BigDecimal.valueOf(quantity)));
        account.setBalance(amount);

        BigDecimal priceForSell = stock.getCurrentPrice();
        BigDecimal avgPrice = getAveragePrice(email, ticker);

        String description = "The user with email \"" + email + "\" sold " + quantity + " stocks with ticker "
                + ticker + ". The amount was transferred to the account: " +
                account.getId() + ". " + calculateProfit(avgPrice, priceForSell, quantity) ;



        Transactions transactions = new Transactions("+" + priceForSell.multiply(BigDecimal.valueOf(quantity))
                .toString() + "₽", "SELL STOCKS", description, account);
        transactionsRepository.save(transactions);

        return portfolioRepository.save(portfolio);
    }

    private BigDecimal getAveragePrice(String email, String ticker) {
        List<StockPurchases> purchases = stockPurchasesRepository.findByUserEmailAndTicker(email, ticker);

        if (purchases.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalCost = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (StockPurchases purchase : purchases) {
            totalCost = totalCost.add(purchase.getPurchasePrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
            totalQuantity += purchase.getQuantity();
        }

        return totalCost.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_UP);
    }

    private String calculateProfit(BigDecimal avgPurchasePrice, BigDecimal sellPrice, int quantity) {
        BigDecimal purchaseCost = avgPurchasePrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal sellAmount = sellPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal difference = sellAmount.subtract(purchaseCost);

        return (difference.compareTo(BigDecimal.ZERO) > 0) ? "Profit: + " + difference + "₽" :
                (difference.compareTo(BigDecimal.ZERO) < 0) ? "Loss: " + difference + "₽" :
                        "No profit or loss";
    }
}
