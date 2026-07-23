package Comparators;
import Models.Order;

import java.util.Comparator;

public class BuyComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {

        if (o1.price != o2.price) {
            // Higher price first
            return Double.compare(o2.price, o1.price);
        }

        // Earlier order first
        return Integer.compare(o1.time, o2.time);
    }
}