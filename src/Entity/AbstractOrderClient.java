package Entity;

import Entity.Order;
import Entity.OrderItem;
import Entity.OrderStatus;

import java.util.Timer;

public abstract class AbstractOrderClient {
    private Order order;
    private AbstractKitchenServer kitchenServer;
    private Timer pollingTimer;

    public void addItemToOrder(OrderItem item) {
        order.addOrderItem(item);
    }

   public void removeItemToOrder(OrderItem item) {
          order.removeOrderItem(item);
    }


   abstract public void submitOrder();

     public abstract void submitOrder(Order order);

     /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */
      abstract protected void startPollingServer(String orderId);

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */
      abstract protected void pickUpOrder();
}