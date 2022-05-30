package Entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends AbstractKitchenServer implements Runnable{
     private ExecutorService threadPool;
     private CompletableFuture<OrderStatus> completableFuture;
     private Map<String, Order> orderMap = new HashMap<>();
     private Buffer buffer;

     public Server(Buffer buffer) {
          threadPool = Executors.newFixedThreadPool(5);
          this.buffer = buffer;
          new Thread(this).start();
     }

     @Override
     public void run() {
          while (!buffer.isEmpty()){
               CompletableFuture.supplyAsync(() -> {
                    try {
                         return receiveOrder(buffer.get());
                    } catch (InterruptedException | IOException e) {
                         throw new RuntimeException(e);
                    }
               }, threadPool).thenAccept(o -> {
                    try {
                         cook(o);
                    } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                    }
               });
          }
     }


     public Order receiveOrder(Order order) throws InterruptedException, IOException {
          Thread.sleep(1500);
          completableFuture = CompletableFuture.supplyAsync(() -> order.getStatus(), threadPool);
          orderMap.put(order.getOrderID(), order);
          order.setStatus(OrderStatus.Received);
          cook(order);
          return order;
     }

     public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
          Thread.sleep(1500);
          completableFuture = CompletableFuture.supplyAsync(() -> {
               Order order = orderMap.get(orderID);
               return order.getStatus();
          });
          return completableFuture;
     }

     public CompletableFuture<OrderStatus> serveOrder(String orderID) throws InterruptedException {
          completableFuture = CompletableFuture.supplyAsync(() -> {
             Order order = orderMap.get(orderID);
             order.setStatus(OrderStatus.Ready);
             return order.getStatus();
          });
          return completableFuture;
     }

     public void cook(Order order) throws InterruptedException {
          Thread.sleep(1500);
          order.setStatus(OrderStatus.BeingPrepared);
          serveOrder(order.getOrderID());
     }

     /*
     public void acceptOrder(Order order) {
          orderMap.put(order.getOrderID(), order);
          CompletableFuture.supplyAsync(() -> {
               try {
                    return receiveOrder(order);
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
          }, threadPool).thenAccept(o -> cook(o));
          System.out.println("Order sent to threadPool");
     }
     */

     /*
     public CompletableFuture<OrderStatus> receiveOrder(Order order) throws InterruptedException {
          Thread.sleep(1500);
          CompletableFuture<OrderStatus> completableFuture = CompletableFuture.supplyAsync(() -> {
               try {
                    return receiveOrder(order);
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
          }, threadPool).thenAccept(o -> cook(o));

          return completableFuture;
     }
     */


     /*
     private void acceptOrder(Order order){
          orderMap.put(order.getOrderID(), order);
          CompletableFuture.supplyAsync(() -> receiveOrder(order), threadPool).thenAccept(o -> cook(o));
          threadPool.submit(receiveOrder(order));
          System.out.println("Order sent to threadPool");
     }
     */


     /*
     private Runnable receiveOrder(Order order){
          Runnable receiveOrder = () -> {
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000,5000));
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
               order.setStatus(OrderStatus.Received);
               try {
                    threadPool.submit(cook(order));
                    //cook(order);
               } catch (IOException e) {
                    throw new RuntimeException(e);
               }
          };
          return receiveOrder;
     }
     */


     /*
     private void cook(Order order) throws IOException {
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(1000,5000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          order.setStatus(OrderStatus.BeingPrepared);
          serveOrder(order);
     }
     */


     /*
     private Runnable cook(Order order) throws IOException {
          Runnable cook = () -> {
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000, 5000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               order.setStatus(OrderStatus.BeingPrepared);
               try {
                    threadPool.submit(serveOrder(order));
                    //serveOrder(order);
               } catch (IOException e) {
                    throw new RuntimeException(e);
               }
          };
          return cook;
     }
     */



/*
     private void serveOrder(Order order) throws IOException {
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(1000,5000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          order.setStatus(OrderStatus.Ready);
     }
     */

     /*
     private Runnable serveOrder(Order order) throws IOException {
          Runnable serveOrder = () -> {
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000,5000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               order.setStatus(OrderStatus.Ready);
          };
          return serveOrder;
     }
     */
}
