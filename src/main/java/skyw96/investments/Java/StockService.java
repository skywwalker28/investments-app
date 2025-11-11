package skyw96.investments.Java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skyw96.investments.Kotlin.DTO.StockDTO;
import skyw96.investments.Kotlin.Exception.*;
import skyw96.investments.Kotlin.Model.Stock;
import skyw96.investments.Kotlin.Repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> viewAll() {
        return stockRepository.findAll();
    }

    public List<StockDTO> viewAllDTO() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private StockDTO convertToDTO(Stock stock) {
        return new StockDTO(
                stock.getTicker(),
                stock.getCompanyName(),
                stock.getCurrentPrice(),
                stock.getPreviousClose(),
                stock.getVolatility(),
                stock.getProfitability()
        );
    }


    public Stock createStock(String ticker, String companyName, BigDecimal currentPrice,
                             BigDecimal previousClose, Double volatility, BigDecimal originalPrice) {
        if (stockRepository.existsByTicker(ticker)) {
            throw new StockAlreadyExistException("Stock with ticker " + ticker + ", already exist");
        }
        checkAdminRole();

        Stock stock = new Stock(ticker, companyName, currentPrice, previousClose,
                volatility, "0.00", originalPrice);
        return stockRepository.save(stock);
    }

    public Stock updateStock(String ticker, BigDecimal newPrice, String newTicker, Double newVolatility
    , String profitability, BigDecimal originalPrice, String companyName) {
        if (!stockRepository.existsByTicker(ticker)) {
            throw new StockNotFoundException("Stock with ticker: " + ticker + ", not found");
        }
        checkAdminRole();

        Stock stock = stockRepository.findByTicker(ticker);


        stock.setCurrentPrice(newPrice);
        stock.setTicker(newTicker);
        stock.setVolatility(newVolatility);
        stock.setProfitability(profitability + "%");
        stock.setProfitability(profitability);
        stock.setOriginalPrice(originalPrice);
        stock.setCompanyName(companyName);

        return stockRepository.save(stock);
    }

    public void checkAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Access denied: ADMIN role required");
        }
    }

}
