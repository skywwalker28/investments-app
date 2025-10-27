package skyw96.investments.Java.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skyw96.investments.Kotlin.Exception.StockNotFoundException;
import skyw96.investments.Kotlin.Model.Stock;
import skyw96.investments.Kotlin.Repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;


@Service
public class StockPriceService {

    @Autowired
    StockRepository stockRepository;

    private final Random random = new Random();

    @Scheduled(fixedRate = 100000)
    public void updateStockPrices() {
        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            throw new StockNotFoundException("No shares of the stocks");
        }

        stocks.forEach( stock -> {
            try {
                BigDecimal newPrice = calculateNewPrice(stock);

                stock.setPreviousClose(stock.getCurrentPrice());
                stock.setCurrentPrice(newPrice);

                String newProfitability = calculateProfitability(stock);
                stock.setProfitability(newProfitability);

                stockRepository.save(stock);
            } catch (Exception e) {
                System.out.println("Error update stock");
            }
        });
    }


    public BigDecimal calculateNewPrice(Stock stock) {

        double changePercent = random.nextDouble(-stock.getVolatility()/100, stock.getVolatility()/100);
        System.out.println("random volatility: \"" + changePercent + "\" for stock with ticker: " + stock.getTicker());
        return stock.getCurrentPrice().multiply(BigDecimal.valueOf(1 + changePercent));
    }

    public String calculateProfitability(Stock stock) {
        double currentPrice = stock.getCurrentPrice().doubleValue();
        double originalPrice = stock.getOriginalPrice().doubleValue();

        double percentage = (currentPrice - originalPrice)/originalPrice * 100;

        return (percentage > 0) ? String.format("+%.2f%%", percentage) : String.format("%.2f%%", percentage);
    }

}
