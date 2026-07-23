import Comparators.BuyComparator;
import Comparators.SellComparator;
import Models.Order;
import Models.OrderTypes;

import java.util.*;

public class OrderBook {

    HashMap<Integer, Order> Orders;
    Set<Integer> Removed;
    PriorityQueue<Order> SellOrderPq;
    PriorityQueue<Order> BuyOrderPq;

    // constructor non params
    OrderBook() {

        Orders = new HashMap<>();
        Removed= new HashSet<>();
        SellOrderPq = new PriorityQueue<>(new SellComparator());
        BuyOrderPq = new PriorityQueue<>(new BuyComparator());
    }


    public void addOrder(Order o) {
        if (o.type == OrderTypes.Types.BUY) {
            Orders.put(o.orderId, o);
            BuyOrderPq.add(o);
        } else {
            Orders.put(o.orderId, o);
            SellOrderPq.add(o);
        }
    }

    public void deleteOrder(Order o) {
        if (o.type == OrderTypes.Types.SELL) {
            Orders.remove(o.orderId);
            Removed.add(o.orderId);
        } else {
            Orders.remove(o.orderId);
            Removed.add(o.orderId);
        }
    }

    public void amendOrder(int OrderId, int qtyNew) {
        if(Orders.get(OrderId).type==OrderTypes.Types.SELL){
//            SellOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).quantity=qtyNew;
            SellOrderPq.add(Orders.get(OrderId));
        }
        else{
//            BuyOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).quantity=qtyNew;
            BuyOrderPq.add(Orders.get(OrderId));
        }
    }

    public void amendOrder(int OrderId, double newprice) {
        if(Orders.get(OrderId).type==OrderTypes.Types.SELL){
//            SellOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).price = newprice;
            SellOrderPq.add(Orders.get(OrderId));
        }
        else{
//            BuyOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).price = newprice;
            BuyOrderPq.add(Orders.get(OrderId));
        }

    }

    public void amendOrder(int OrderId , int qtyNew , double newprice){
        if(Orders.get(OrderId).type==OrderTypes.Types.SELL){
//            SellOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).price = newprice;
            Orders.get(OrderId).quantity=qtyNew;
            SellOrderPq.add(Orders.get(OrderId));
        }
        else{
//            BuyOrderPq.remove(Orders.get(OrderId));
            Removed.add(OrderId);
            Orders.get(OrderId).price = newprice;
            Orders.get(OrderId).quantity=qtyNew;
            BuyOrderPq.add(Orders.get(OrderId));
        }
    }

    public void printOrderBook() {
        System.out.println("orderId time quantity price type");

        // Print Sell Orders after converting priority queue to array
        Order[] sellArray = SellOrderPq.toArray(new Order[0]);
        Arrays.sort(sellArray, SellOrderPq.comparator());

        for (Order o : sellArray) {
            System.out.println(o.orderId + " " +
                    o.time + " " +
                    o.quantity + " " +
                    o.price + " " +
                    o.type);
        }

        // Print Buy Orders
        Order[] buyArray = BuyOrderPq.toArray(new Order[0]);
        Arrays.sort(buyArray, BuyOrderPq.comparator());

        for (Order o : buyArray) {
            System.out.println(o.orderId + " " +
                    o.time + " " +
                    o.quantity + " " +
                    o.price + " " +
                    o.type);
        }
    }

    public PriorityQueue<Order> getBuyOrderPq() {
        return BuyOrderPq;
    }

    public PriorityQueue<Order> getSellOrderPq() {
        return SellOrderPq;
    }

    public void setSellOrderPq(PriorityQueue<Order> sellOrderPq) {
        SellOrderPq = sellOrderPq;
    }

    public void setBuyOrderPq(PriorityQueue<Order> buyOrderPq) {
        BuyOrderPq = buyOrderPq;
    }

}