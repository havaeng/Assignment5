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
     private Map<String, Order> orderMap;
     private Buffer buffer;

     public Server(Buffer buffer) {
          threadPool = Executors.newFixedThreadPool(5);
          this.buffer = buffer;
          orderMap = new HashMap<>();
          new Thread(this).start();
     }

     @Override
     public void run() {
          while (true) {
               while (!buffer.isEmpty()) {
                    CompletableFuture.supplyAsync(() -> {
                         try {
                              return receiveOrder();
                         } catch (InterruptedException | IOException e) {
                              throw new RuntimeException(e);
                         }
                    }, threadPool).thenApplyAsync(o -> {
                         try {
                              return cook(o);
                         } catch (InterruptedException e) {
                              throw new RuntimeException(e);
                         }
                    }, threadPool).thenAcceptAsync(o -> serveOrder(o), threadPool);
               }
          }
     }


     public Order receiveOrder() throws InterruptedException, IOException {
          Order order = buffer.get();
          System.out.println("Receive order");
          orderMap.put(order.getOrderID(), order);
          System.out.println(orderMap.size());
          order.setStatus(OrderStatus.Received);
          Thread.sleep(1500);
          return order;
     }

     public OrderStatus checkStatus(String orderID) throws InterruptedException {
          //System.out.println(orderID);
          //System.out.println(orderMap.size());
          if (orderMap.containsKey(orderID)){
               Order order = orderMap.get(orderID);
               return order.getStatus();
          }
          return OrderStatus.NotFound;
     }

     public void serveOrder(Order order) {
             order.setStatus(OrderStatus.Ready);
             order.setDone(true);
     }

     public Order cook(Order order) throws InterruptedException {
          order.setStatus(OrderStatus.BeingPrepared);
         // serveOrder(order.getOrderID());
          Thread.sleep(3000);
          return order;
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
