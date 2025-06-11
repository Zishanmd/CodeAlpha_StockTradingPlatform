import java.util.*;

class Stock {
    String name;
    String symbol;
    double price;

    Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    void updatePrice() {
        double change = (Math.random() * 20 - 10);
        price = Math.max(price + change, 1);
    }

    @Override
    public String toString() {
        return symbol + " - " + name + ": ₹" + String.format("%.2f", price);
    }
}

class Portfolio {
    Map<String, Integer> stocks = new HashMap<>();
    double balance = 10000;

    void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (balance >= cost) {
            stocks.put(symbol, stocks.getOrDefault(symbol, 0) + quantity);
            balance -= cost;
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void sellStock(String symbol, int quantity, double price) {
        int owned = stocks.getOrDefault(symbol, 0);
        if (owned >= quantity) {
            stocks.put(symbol, owned - quantity);
            balance += quantity * price;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("You don't own enough shares.");
        }
    }

    void viewPortfolio(Map<String, Stock> market) {
        System.out.println("Your Portfolio:");
        double totalValue = balance;
        for (String symbol : stocks.keySet()) {
            int quantity = stocks.get(symbol);
            double price = market.get(symbol).price;
            System.out.println(symbol + ": " + quantity + " shares | ₹" + String.format("%.2f", price) + " each.");
            totalValue += quantity * price;
        }
        System.out.println("Cash Balance: ₹" + String.format("%.2f", balance));
        System.out.println("Total Portfolio Value: ₹" + String.format("%.2f", totalValue));
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Stock> market = new HashMap<>();
        Portfolio portfolio = new Portfolio();

        market.put("TCS", new Stock("Tata Consultancy Services", "TCS", 3400));
        market.put("INFY", new Stock("Infosys", "INFY", 1450));
        market.put("RELI", new Stock("Reliance", "RELI", 2500));

        while (true) {
            System.out.println("\n....STOCK TRADING PLATFORM....");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Live Market Data:");
                    for (Stock s : market.values()) {
                        s.updatePrice();
                        System.out.println(s);
                    }
                }
                case 2 -> {
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (!market.containsKey(buySymbol)) {
                        System.out.println("Invalid symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    portfolio.buyStock(buySymbol, buyQty, market.get(buySymbol).price);
                }
                case 3 -> {
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (!market.containsKey(sellSymbol)) {
                        System.out.println("Invalid symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    portfolio.sellStock(sellSymbol, sellQty, market.get(sellSymbol).price);
                }
                case 4 -> portfolio.viewPortfolio(market);
                case 5 -> {
                    System.out.println("Exiting......");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
