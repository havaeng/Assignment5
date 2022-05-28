package Entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderID;
    private List<OrderItem> orderList;
    private boolean sent;
    private boolean done;
    private OrderStatus status = OrderStatus.NotSent;
    private static int nextOrderID = 1;

    public Order() {
       this.orderID = String.valueOf(nextOrderID);
       nextOrderID++;
       orderList = new ArrayList<>();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getOrderID() {
        return orderID;
    }

    public void addOrderItem(OrderItem item) {
        orderList.add(item);
    }

    public void removeOrderItem(OrderItem item) {
        orderList.remove(item);
    }

    public List<OrderItem> getOrderList() {
        return orderList;
    }

    public String[] getNamesFromOrderList() {
        return orderList.stream().map(item -> item.getName()).toArray(String[]::new);
    }

    public void clear() {
        orderList.clear();
    }
}