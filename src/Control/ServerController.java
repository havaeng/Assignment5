package Control;

import Entity.AbstractKitchenServer;
import Entity.Order;
import Entity.OrderStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ServerController extends AbstractKitchenServer {
    Map<String, Order> orderMap = new HashMap<>();

    @Override
    public CompletableFuture<OrderStatus> receiveOrder(Order order) throws InterruptedException {
        System.out.println("Order " + order.getOrderID() + "got recieved");
        CompletableFuture<OrderStatus> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return order.getStatus();
        });
        orderMap.put(order.getOrderID(), order);
        order.setStatus(OrderStatus.Received);
        cook(order);
        return completableFuture;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        CompletableFuture<OrderStatus> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Order order = orderMap.get(orderID);
            return order.getStatus();
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<OrderStatus> serveOrder(String orderID) throws InterruptedException {
        System.out.println("Order " + orderID + " ready and served");
        CompletableFuture<OrderStatus> completableFuture = CompletableFuture.supplyAsync(() -> {
            Order order = orderMap.get(orderID);
            order.setStatus(OrderStatus.Ready);
            return order.getStatus();
        });
        return completableFuture;
    }

    @Override
    protected void cook(Order order) {
        System.out.println("Order " + order.getOrderID() + " getting cooked");
        order.setStatus(OrderStatus.BeingPrepared);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            serveOrder(order.getOrderID());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
