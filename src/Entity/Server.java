package Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends AbstractKitchenServer {
     private Buffer buffer;
     private Map<String, Order> orderMap = new HashMap<>();

     public Server(Buffer buffer) {
          this.buffer = buffer;
          //threadPool = Executors.newFixedThreadPool(5);
     }

     @Override
     public CompletableFuture<OrderStatus> receiveOrder(Order order) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<OrderStatus> serveOrder(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     protected void cook(Order order) {

     }
}
