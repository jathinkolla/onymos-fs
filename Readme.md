# Onymos Stock Trading Engine

## 📌 Overview
This project is a real-time **Stock Trading Engine** designed as part of the Onymos Hiring coding challenge. It implements an efficient order matching system that processes buy and sell transactions while ensuring **thread safety** and **lock-free** operations.

The solution adheres to the following requirements:
- **Supports 1,024 tickers (stocks) being traded**.
- **Implements an `addOrder` function** that randomly executes buy and sell transactions.
- **Includes a `matchOrder` function** that efficiently matches buy and sell orders with an O(n) complexity.
- **Ensures race-condition handling in a multi-threaded environment**.
- **Strictly avoids dictionaries, maps, or any equivalent data structures**.

---

## 🚀 **How to Run the Code**
To execute the simulation, follow these steps:

### **1️⃣ Clone the Repository**
git clone https://github.com/jathinkolla/onymos-fs.git

### **2️⃣ Compile the Java Code**
javac -d bin src/*.java

### **3️⃣ Run the Simulation**
java -cp bin Simulation

## **📜 Solution Breakdown**

### **1️⃣ addOrder Function**
- Adds a buy or sell order for a stock ticker.
- Ensures a lock-free, thread-safe order book.
- Randomly simulates stock transactions.

### **2️⃣ matchOrder Function**
- Matches buy and sell orders when the buy price is greater than or equal to the lowest available sell price.
- Implements an efficient O(n) complexity matching algorithm.
- Ensures that multiple stockbrokers can modify the order book without race conditions.

