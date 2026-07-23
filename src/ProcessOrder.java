import Models.Order;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ProcessOrder {

    void process(OrderBook orderbook, OrderPool op ){

        PriorityQueue<Order> Sellpq = orderbook.getSellOrderPq();
        PriorityQueue<Order> Buypq = orderbook.getBuyOrderPq();
        if(Sellpq.isEmpty() || Buypq.isEmpty()){
            return;
        }
        Order prSell = Sellpq.peek();
        Order prBuy  = Buypq.peek();
        while(!Sellpq.isEmpty() && orderbook.Removed.contains(prSell.orderId)){
            op.releaseOrder(prSell.poolIndex);
            Sellpq.poll();
            if(!Sellpq.isEmpty())prSell=Sellpq.peek();
        }


        while(!Buypq.isEmpty() && orderbook.Removed.contains(prBuy.orderId)){
            op.releaseOrder(prBuy.poolIndex);
            Buypq.poll();
            if(!Buypq.isEmpty())prBuy=Buypq.peek();
        }



        // cannot match
        if(prSell.price> prBuy.price){
            return;
        }

        while(!Sellpq.isEmpty() && !Buypq.isEmpty() && Sellpq.peek().price<= Buypq.peek().price){
            prSell=Sellpq.peek();
            prBuy=Buypq.peek();

            while(!Sellpq.isEmpty() && orderbook.Removed.contains(prSell.orderId)){
                op.releaseOrder(prSell.poolIndex);
                Sellpq.poll();
                if(!Sellpq.isEmpty())prSell=Sellpq.peek();
            }
            while(!Buypq.isEmpty() && orderbook.Removed.contains(prBuy.orderId)){
                op.releaseOrder(prBuy.poolIndex);
                Buypq.poll();
                if(!Buypq.isEmpty())prBuy=Buypq.peek();
            }

            if(prSell.quantity<prBuy.quantity){
                // matching this pr sell with pr buy
                op.releaseOrder(Sellpq.peek().poolIndex);
                op.releaseOrder(Buypq.peek().poolIndex);
                Sellpq.poll();
                Buypq.poll();

//                Order newRemInBuy = new Order(prBuy.orderId,prBuy.type,prBuy.price,prBuy.quantity-prSell.quantity,prBuy.time, prBuy.poolIndex);
                Order newRemInBuy = op.getOrder(prBuy.orderId,prBuy.type,prBuy.price,prBuy.quantity-prSell.quantity,prBuy.time);
                orderbook.Orders.put(newRemInBuy.orderId, newRemInBuy);
                Buypq.add(newRemInBuy);
                int tradedQty = Math.min(prSell.quantity, prBuy.quantity);

                System.out.println(
                        "TRADE :: Buy Id: " + prBuy.orderId +
                                " Sell Id: " + prSell.orderId +
                                " Qty: " + tradedQty +
                                " Price: " + prSell.price
                );


            }
            else{

                if(prSell.quantity==prBuy.quantity){
                    op.releaseOrder(Sellpq.peek().poolIndex);
                    op.releaseOrder(Buypq.peek().poolIndex);
                    Sellpq.poll();
                    Buypq.poll();

                    int tradedQty = Math.min(prSell.quantity, prBuy.quantity);

                    System.out.println(
                            "TRADE :: Buy Id: " + prBuy.orderId +
                                    " Sell Id: " + prSell.orderId +
                                    " Qty: " + tradedQty +
                                    " Price: " + prSell.price
                    );
                    continue;
                }
                op.releaseOrder(Sellpq.peek().poolIndex);
                op.releaseOrder(Buypq.peek().poolIndex);
                Sellpq.poll();
                Buypq.poll();

//                Order newRemInSell = new Order(prSell.orderId,prSell.type,prSell.price,prSell.quantity-prBuy.quantity,prSell.time,prSell.poolIndex);
                Order newRemInSell = op.getOrder(prSell.orderId,prSell.type,prSell.price,prSell.quantity-prBuy.quantity,prSell.time);

                Sellpq.add(newRemInSell);
                int tradedQty = Math.min(prSell.quantity, prBuy.quantity);

                System.out.println(
                        "TRADE :: Buy Id: " + prBuy.orderId +
                                " Sell Id: " + prSell.orderId +
                                " Qty: " + tradedQty +
                                " Price: " + prSell.price
                );
            }
        }


        orderbook.setBuyOrderPq(Buypq);
        orderbook.setSellOrderPq(Sellpq);

    }

}
