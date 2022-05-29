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
                    if (!(order == null)) {
                         acceptOrder(order);
                         System.out.println("Order received by server");
                    }
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               }
          }
     }

     private void acceptOrder(Order order){
          orderMap.put(order.getOrderID(), order);
          threadPool.submit(receiveOrder(order));
          System.out.println("Order sent to threadPool");
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
                    threadPool.submit(cook(order));
                    //cook(order);
               } catch (IOException e) {
                    throw new RuntimeException(e);
               }
          };
          return receiveOrder;
     }
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
}
