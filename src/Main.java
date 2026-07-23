import Models.Order;
import Models.OrderTypes;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        OrderBook orderBook = new OrderBook();
        ProcessOrder processOrder= new ProcessOrder();
        int iterations = 10_000_000;

        long startTime = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            // Generate random type (50% chance of BUY or SELL)
            OrderTypes.Types randomType = ThreadLocalRandom.current().nextBoolean() ?
                    OrderTypes.Types.BUY : OrderTypes.Types.SELL;

            // generate random price (between 100.0 and 200.0)
            double randomPrice = ThreadLocalRandom.current().nextDouble(100.0, 200.0);

            // Generate random quantity (between 1 and 100)
            int randomQty = ThreadLocalRandom.current().nextInt(1, 101);

            orderBook.addOrder(new Order(i, randomType, randomPrice, randomQty, i));
            processOrder.process(orderBook);
        }

        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;
        double seconds = durationMs / 1000.0;
        double opsPerSec = seconds > 0 ? iterations / seconds : 0;

        System.out.println("   Benchmark Results ");
        System.out.println("Total Operations : " + iterations);
        System.out.println("Total Time       : " + durationMs + " ms");
        System.out.printf("Throughput       : %,.0f ops/sec\n", opsPerSec);

    }
}
