package Control;

import Boundary.RestaurantGUI;
import Entity.AbstractOrderClient;
import Entity.Order;
import Entity.OrderItem;
import Entity.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientController extends AbstractOrderClient {
     private Timer timer;
     private ServerController server;
     private RestaurantGUI gui;
     private Order order;
     private List<Order> orders = new ArrayList<>();
     private OrderItem orderItem = new OrderItem();

     public ClientController(ServerController server) {
          this.server = server;
          gui = new RestaurantGUI(this);
          order = new Order();
     }

     @Override
     public void submitOrder() {
          try {
               if (!order.getOrderList().isEmpty()) {
                    server.receiveOrder(order).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
                    startPollingServer(order.getOrderID());
                    orders.add(order);
                    gui.clearOrder();
                    order = new Order();
               }
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
     }

     @Override
     protected void startPollingServer(String orderId) {
          TimerTask task = new TimerTask() {
               public void run() {
                    try {
                         server.checkStatus(orderId).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
                         if(orderStatusHallo == OrderStatus.Ready){
                              pickUpOrder();
                              timer.cancel();
                         }
                    } catch (InterruptedException e) {
                         e.printStackTrace();
                    }
               }
          };
          Timer timer = new Timer("Timer");

          long delay = 1000L;
          timer.schedule(task, delay);
     }

     @Override
     protected synchronized void pickUpOrder() {

     }

     public void addToOrderList(String string) {
          switch (string){
               case "sandwich" -> {
                    order.addOrderItem(orderItem.sandwich());
                    gui.updateOrderCart("Sandwich");
               }
               case "borscht" -> {
                    order.addOrderItem(orderItem.borscht());
                    gui.updateOrderCart("Borscht");
               }
               case "coffee" -> {
                    order.addOrderItem(orderItem.coffee());
                    gui.updateOrderCart("Coffee");
               }
          }
     }

     public void remove(){
          gui.clearOrder();
          order.clear();
     }
}
