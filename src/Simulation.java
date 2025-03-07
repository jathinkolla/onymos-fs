public class Simulation {
    private static final int MAX_TICKERS = 1024;
    private static final String[] TICKER_LIST = new String[MAX_TICKERS];

    // Initialize ticker symbols (TCK1 to TCK1024)
    static {
        for (int i = 0; i < MAX_TICKERS; i++) {
            TICKER_LIST[i] = "TCK" + (i + 1);
        }
    }

    // Retrieve ticker symbol by index
    public static String getTicker(int index) {
        return (index >= 0 && index < MAX_TICKERS) ? TICKER_LIST[index] : null;
    }

    // Get total number of tickers
    public static int getTotalTickers() {
        return MAX_TICKERS;
    }

    public static void main(String[] args) {
        System.out.println("Stock Trading Simulation Started...");

        // Generate a random number of iterations (100 to 999)
        int randomIterations = (int) (Math.random() * 900) + 100; 

        System.out.println("Running simulation with " + randomIterations + " iterations.");
        
        simulateTrading(randomIterations);
        OrderMatchingEngine.matchOrder();
        
        System.out.println("Trading Simulation Completed!");
    }

    // Simulates placing random buy/sell orders
    public static void simulateTrading(int iterations) {
        for (int i = 0; i < iterations; i++) {
            String orderType = Math.random() > 0.5 ? "Buy" : "Sell";
            int tickerIndex = (int) (Math.random() * MAX_TICKERS);
            int quantity = (int) (Math.random() * 100) + 1;

            // Generate a base price within a realistic range
            double basePrice = Math.round((Math.random() * 500) + 100) * 100.0 / 100.0;
            double price = "Buy".equalsIgnoreCase(orderType) ? basePrice + Math.random() * 50 : basePrice - Math.random() * 50;
            price = Math.round(price * 100.0) / 100.0; // Round to 2 decimal places

            StockTradingEngine.addOrder(orderType, tickerIndex, quantity, price);
        }
        System.out.println("Order Generation Completed!");
    }
}

// Represents a stock order (Buy/Sell)
class Order {
    String orderType;
    int tickerIndex;
    int quantity;
    double price;

    public Order(String orderType, int tickerIndex, int quantity, double price) {
        this.orderType = orderType;
        this.tickerIndex = tickerIndex;
        this.quantity = quantity;
        this.price = Math.round(price * 100.0) / 100.0; // Round to 2 decimal places
    }
}
