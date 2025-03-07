public class StockTradingEngine {
    private static final int MAX_ORDERS = 10000;
    private static final int TOTAL_TICKERS = Simulation.getTotalTickers();

    // Arrays to store buy and sell orders for each ticker
    private static Order[][] buyOrders = new Order[TOTAL_TICKERS][MAX_ORDERS];
    private static Order[][] sellOrders = new Order[TOTAL_TICKERS][MAX_ORDERS];

    // Counters to track the number of buy and sell orders for each ticker
    private static int[] buyCount = new int[TOTAL_TICKERS];
    private static int[] sellCount = new int[TOTAL_TICKERS];

    // Adds a new order (Buy or Sell) to the order book
    public static void addOrder(String orderType, int tickerIndex, int quantity, double price) {
        if (tickerIndex < 0 || tickerIndex >= TOTAL_TICKERS) return;

        Order newOrder = new Order(orderType, tickerIndex, quantity, price);
        int orderIndex;

        if ("Buy".equalsIgnoreCase(orderType)) {
            orderIndex = buyCount[tickerIndex]++;
            if (orderIndex < MAX_ORDERS) {
                buyOrders[tickerIndex][orderIndex] = newOrder;
                System.out.println("Added BUY Order: " + quantity + " shares of " +
                        Simulation.getTicker(tickerIndex) + " at $" + price);
            }
        } else if ("Sell".equalsIgnoreCase(orderType)) {
            orderIndex = sellCount[tickerIndex]++;
            if (orderIndex < MAX_ORDERS) {
                sellOrders[tickerIndex][orderIndex] = newOrder;
                System.out.println("Added SELL Order: " + quantity + " shares of " +
                        Simulation.getTicker(tickerIndex) + " at $" + price);
            }
        }
    }

    // Returns the count of buy orders for a given ticker
    public static int getBuyCount(int tickerIndex) {
        return buyCount[tickerIndex];
    }

    // Returns the count of sell orders for a given ticker
    public static int getSellCount(int tickerIndex) {
        return sellCount[tickerIndex];
    }

    // Retrieves a buy order for a given ticker at a specific index
    public static Order getBuyOrder(int tickerIndex, int index) {
        return buyOrders[tickerIndex][index];
    }

    // Retrieves a sell order for a given ticker at a specific index
    public static Order getSellOrder(int tickerIndex, int index) {
        return sellOrders[tickerIndex][index];
    }

    // Removes a buy order after matching
    public static void removeBuyOrder(int tickerIndex, int index) {
        buyOrders[tickerIndex][index] = null;
    }

    // Removes a sell order after matching
    public static void removeSellOrder(int tickerIndex, int index) {
        sellOrders[tickerIndex][index] = null;
    }
}
