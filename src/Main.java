import Models.Order;
import Models.OrderTypes;

public class Main {

    public static void main(String[] args) {

        OrderBook orderBook = new OrderBook();

        // Sell Orders
        orderBook.addOrder(new Order(1, OrderTypes.Types.SELL, 100.0, 10, 1));
        orderBook.addOrder(new Order(2, OrderTypes.Types.SELL, 102.0, 5, 2));
        orderBook.addOrder(new Order(3, OrderTypes.Types.SELL, 101.0, 8, 3));

        // Buy Orders
        orderBook.addOrder(new Order(4, OrderTypes.Types.BUY, 99.0, 6, 4));
        orderBook.addOrder(new Order(5, OrderTypes.Types.BUY, 101.0, 12, 5));
        orderBook.addOrder(new Order(6, OrderTypes.Types.BUY, 103.0, 7, 6));

        System.out.println("Before Matching:");
        orderBook.printOrderBook();

        ProcessOrder matcher = new ProcessOrder();
        matcher.process(orderBook);

        System.out.println("\nAfter Matching:");
        orderBook.printOrderBook();
    }
}