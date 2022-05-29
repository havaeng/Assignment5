package Entity;

import java.util.Timer;

public abstract class AbstractOrderClient {
    private Order order;
    private AbstractKitchenServer kitchenServer;
    private Timer pollingTimer;

     public abstract void submitOrder();

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
      abstract protected void pickUpOrder(String orderId);
}