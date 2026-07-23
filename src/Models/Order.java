package Models;

public class Order{
    public Order(int orderId, OrderTypes.Types type, double price, int quantity, int time, int poolIndex) {
        this.orderId = orderId;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.poolIndex=poolIndex;
    }

    public int orderId;
    public OrderTypes.Types type;
    public double price ;
    public int quantity;
    public int time;
    public int poolIndex;

    public Order() {
        this.orderId = -1;
        this.type = OrderTypes.Types.SELL;
        this.price = 0;
        this.quantity = -1;
        this.time = -1;
        this.poolIndex=-1;

    }
}
