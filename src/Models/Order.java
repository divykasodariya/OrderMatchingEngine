package Models;

import java.time.LocalTime;
public class Order{
    public Order(int orderId, OrderTypes.Types type, double price, int quantity, int time) {
        this.orderId = orderId;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
    }

    public int orderId;
    public OrderTypes.Types type;
    public double price ;
    public int quantity;
    public int time;
}
