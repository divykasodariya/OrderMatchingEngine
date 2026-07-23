import Models.Order;
import Models.OrderTypes;

public class OrderPool {
    private final Order[] pool;
    private final int[] freeIndices;
    private int head;

    public OrderPool(int capacity) {
        pool = new Order[capacity];
        freeIndices = new int[capacity];

        for (int i = 0; i < capacity; i++) {
            pool[i] = new Order();
            freeIndices[i] = i;
        }

        head = capacity - 1;
    }

    // O(1)
    public Order getOrder(int orderId, OrderTypes.Types type, double price, int quantity, long time) {
        if (head<0) {
            throw new RuntimeException("Order Pool Exhausted");
        }

        // Pop the next available index off
        int index = freeIndices[head--];

        //actual reference to objcet;
        Order o = pool[index];

        // Overwrite the fields
        o.orderId = orderId;
        o.type = type;
        o.price = price;
        o.quantity = quantity;
        o.time = Math.toIntExact(time);
        o.poolIndex = index;

        return o;
    }


    public void releaseOrder(int indexToFree) {
        // Push the index back
        freeIndices[++head] = indexToFree;
    }
}