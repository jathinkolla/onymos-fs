import threading
import random
import time

class Order:
    """Class to represent a single order."""
    def __init__(self, order_type, ticker, quantity, price):
        self.order_type = order_type  # "Buy" or "Sell"
        self.ticker = ticker          # Stock symbol
        self.quantity = quantity      # Number of shares
        self.price = price            # Price per share
        self.next = None              # Pointer for linked list structure

class OrderBook:
    """Class to manage the order book with Buy and Sell linked lists."""
    def __init__(self):
        self.buy_orders = None  # Buy orders sorted in descending price
        self.sell_orders = None  # Sell orders sorted in ascending price
        self.lock = threading.Lock()

    def insert_order(self, order):
        """Insert order into the correct list (Buy/Sell) in sorted order."""
        if order.order_type == "Buy":
            self.buy_orders = self._insert_sorted(self.buy_orders, order, descending=True)
        else:
            self.sell_orders = self._insert_sorted(self.sell_orders, order, descending=False)

    def _insert_sorted(self, head, order, descending=False):
        """Helper function to insert order into a sorted linked list."""
        if not head or (descending and order.price > head.price) or (not descending and order.price < head.price):
            order.next = head
            return order

        prev, curr = None, head
        while curr and ((descending and order.price <= curr.price) or (not descending and order.price >= curr.price)):
            prev, curr = curr, curr.next

        prev.next, order.next = order, curr
        return head

    def match_orders(self):
        """Match Buy and Sell orders."""
        with self.lock:
            while self.buy_orders and self.sell_orders and self.buy_orders.price >= self.sell_orders.price:
                matched_quantity = min(self.buy_orders.quantity, self.sell_orders.quantity)
                
                print(f"Matched {matched_quantity} shares of {self.buy_orders.ticker} at ${self.sell_orders.price}")

                # Update Buy Order
                if self.buy_orders.quantity == matched_quantity:
                    self.buy_orders = self.buy_orders.next
                else:
                    self.buy_orders.quantity -= matched_quantity

                # Update Sell Order
                if self.sell_orders.quantity == matched_quantity:
                    self.sell_orders = self.sell_orders.next
                else:
                    self.sell_orders.quantity -= matched_quantity

    def addOrder(self, order_type, ticker, quantity, price):
        """Public function to add a new order and trigger matching."""
        order = Order(order_type, ticker, quantity, price)
        self.insert_order(order)
        self.match_orders()

# Simulate Random Orders
def simulate_orders(order_book):
    tickers = ["AAPL", "GOOGL", "TSLA", "MSFT", "AMZN"]
    while True:
        order_type = random.choice(["Buy", "Sell"])
        ticker = random.choice(tickers)
        quantity = random.randint(1, 100)
        price = random.randint(100, 500)
        
        order_book.addOrder(order_type, ticker, quantity, price)
        time.sleep(random.uniform(0.1, 0.5))  # Simulate stock transactions

# Run the Trading Engine
if __name__ == "__main__":
    order_book = OrderBook()
    threads = [threading.Thread(target=simulate_orders, args=(order_book,)) for _ in range(5)]
    
    for t in threads:
        t.start()
    
    for t in threads:
        t.join()
