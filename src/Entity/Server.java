package Entity;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class Server extends AbstractKitchenServer implements Runnable{
     private ExecutorService threadPool;

     public Server() {

     }

          private void serveOrder() throws IOException {
       //        this.order.setStatus(OrderStatus.Ready);
         //      statusInfo(order.getStatus());
          }

          public void setOrder(Order order) {
        //       this.order = order;
          }

     @Override
     public CompletableFuture<OrderStatus> receiveOrder(Order order) throws InterruptedException {
          order.setStatus(OrderStatus.Received);
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(5000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          cook(order);
          return null;//OrderStatus.Received;
     }

     @Override
     public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<OrderStatus> serveOrder(String orderID) throws InterruptedException {
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(2000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          return null;
     }

     @Override
     protected void cook(Order order) {
               order.setStatus(OrderStatus.BeingPrepared);
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(6000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          try {
               serveOrder(order.getOrderID());
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
     }

     @Override
     public void run() {

     }
}
