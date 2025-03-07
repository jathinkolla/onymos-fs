public class OrderMatchingEngine {
    // Matches buy and sell orders for each ticker
    public static void matchOrder() {
        System.out.println("Matching Orders...");

        for (int tickerIndex = 0; tickerIndex < Simulation.getTotalTickers(); tickerIndex++) {
            if (StockTradingEngine.getBuyCount(tickerIndex) == 0 || StockTradingEngine.getSellCount(tickerIndex) == 0) continue;

            // Retrieve buy and sell orders for the current ticker
            Order[] buyOrders = fetchOrders(tickerIndex, true);
            Order[] sellOrders = fetchOrders(tickerIndex, false);

            // Sort orders (Buy: highest price first, Sell: lowest price first)
            sortDescending(buyOrders);
            sortAscending(sellOrders);

            processMatching(buyOrders, sellOrders, tickerIndex);
        }
    }

    // Retrieves buy or sell orders for a specific ticker
    private static Order[] fetchOrders(int tickerIndex, boolean isBuyOrder) {
        int count = isBuyOrder ? StockTradingEngine.getBuyCount(tickerIndex) : StockTradingEngine.getSellCount(tickerIndex);
        Order[] orders = new Order[count];

        for (int i = 0; i < count; i++) {
            orders[i] = isBuyOrder ? StockTradingEngine.getBuyOrder(tickerIndex, i) : StockTradingEngine.getSellOrder(tickerIndex, i);
        }
        return orders;
    }

    // Processes order matching between buy and sell orders
    private static void processMatching(Order[] buyOrders, Order[] sellOrders, int tickerIndex) {
        int buyIndex = 0, sellIndex = 0;

        while (buyIndex < buyOrders.length && sellIndex < sellOrders.length) {
            Order buyOrder = buyOrders[buyIndex];
            Order sellOrder = sellOrders[sellIndex];

            System.out.println("Checking " + Simulation.getTicker(tickerIndex) +
                    " | BUY: $" + buyOrder.price + " | SELL: $" + sellOrder.price);

            double midpointPrice = Math.round(((buyOrder.price + sellOrder.price) / 2) * 100.0) / 100.0;

            if (buyOrder.price >= sellOrder.price || midpointPrice >= sellOrder.price) {
                int matchedQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);

                System.out.println("MATCH FOUND! " + matchedQuantity + " shares at $" + midpointPrice);

                buyOrder.quantity -= matchedQuantity;
                sellOrder.quantity -= matchedQuantity;

                if (buyOrder.quantity == 0) StockTradingEngine.removeBuyOrder(tickerIndex, buyIndex++);
                if (sellOrder.quantity == 0) StockTradingEngine.removeSellOrder(tickerIndex, sellIndex++);
            } else {
                sellIndex++;
            }
        }
    }

    // Sorts orders in descending order (highest price first)
    private static void sortDescending(Order[] orders) {
        bubbleSort(orders, false);
    }

    // Sorts orders in ascending order (lowest price first)
    private static void sortAscending(Order[] orders) {
        bubbleSort(orders, true);
    }

    // Bubble sort helper function
    private static void bubbleSort(Order[] orders, boolean ascending) {
        for (int i = 0; i < orders.length - 1; i++) {
            for (int j = 0; j < orders.length - i - 1; j++) {
                if (orders[j] != null && orders[j + 1] != null) {
                    boolean condition = ascending ? orders[j].price > orders[j + 1].price : orders[j].price < orders[j + 1].price;
                    if (condition) {
                        Order temp = orders[j];
                        orders[j] = orders[j + 1];
                        orders[j + 1] = temp;
                    }
                }
            }
        }
    }
}
