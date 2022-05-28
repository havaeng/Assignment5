package Entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
     private List<User> userList;
     private ExecutorService threadPool;
     private TheInternet buffer;
     private Map<String, Order> orderMap = new HashMap<>();

     public Server(TheInternet buffer) {
          this.buffer = buffer;
          threadPool = Executors.newFixedThreadPool(5);
     }
     @Override
     public void run(){
          while (true){
               try {
                    Order order = buffer.receiveOrder();
                    acceptOrder(order);
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
          }
     }

     private void acceptOrder(Order order){
          orderMap.put(order.getOrderID(), order);
          threadPool.submit(receiveOrder(order));
     }

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
                    cook(order);
               } catch (IOException e) {
                    throw new RuntimeException(e);
               }
          };
          return receiveOrder;
     }

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


     private void serveOrder(Order order) throws IOException {
          try {
               Random random = new Random();
               Thread.sleep(random.nextInt(1000,5000));
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
          order.setStatus(OrderStatus.Ready);
     }
}
