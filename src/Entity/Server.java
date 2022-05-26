package Entity;

import Control.AbstractKitchenServer;

import java.util.concurrent.CompletableFuture;

public class Server extends AbstractKitchenServer implements Runnable {


     @Override
     public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     protected void cook(Order order) {

     }

     @Override
     public void run() {

     }
}
