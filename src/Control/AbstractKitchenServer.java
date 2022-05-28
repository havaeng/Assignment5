package Control;

import Entity.Order;
import Entity.OrderStatus;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;


 public abstract class AbstractKitchenServer {
  ExecutorService threadPool;
  Map<String, Order> orderMap;

    /**
     * This method should save the order to the map
     * and return a confirmation that the order is received {@link KitchenStatus#Received}
     * or a rejection {@link KitchenStatus#Rejected}
     *
     * When an order is received, a {@link #cook(Order)} task should be launched in the {@link #threadPool}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */
   //abstract public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException;

    /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */
    abstract public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException;

    /**
     * Allows a client to picks up the order if it is ready {@link OrderStatus#Ready}.
     * Should remove the order from the {@link #orderMap}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */
    //abstract public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException;

    /**
     * Simulate cooking in this method.
     * Execute random delay and update the order status
     * {@link OrderStatus#Received} -> {@link OrderStatus#BeingPrepared} -> {@link OrderStatus#Ready}
     */
    abstract protected void cook(Order order);
}
