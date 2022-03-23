package tqs;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        stocks = new ArrayList<Stock>();
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            double price = stockmarket.lookUpPrice(stock.getLabel());
            total += price * stock.getQuantity();
        }
        return total;
    }

}
