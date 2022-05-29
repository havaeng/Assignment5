package Entity;

import Boundary.RestaurantGUI;
import Control.Controller;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Client extends AbstractOrderClient{
     private Order order;
     private Server server;
     private Timer timer;
     private RestaurantGUI gui;
     private List<Order> orders = new ArrayList<>();
     private OrderItem orderItem = new OrderItem();

     public Client(Server server, Controller controller) {
          this.server = server;
          gui = new RestaurantGUI(controller);
          order = new Order();
     }

     public void submitOrder() {
          try {
               if (!order.getOrderList().isEmpty()) {
                    server.receiveOrder(order).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
                    startPollingServer(order.getOrderID());
                    orders.add(order);
                    gui.clearOrder();
                    order = new Order();
               } else if (order.getOrderList().isEmpty()) {
                    JOptionPane.showMessageDialog(
                              null,
                              "Order is empty. Try again.",
                              "Warning", JOptionPane.WARNING_MESSAGE,
                              null);
               }
          } catch (InterruptedException e) {
               e.printStackTrace();
          } catch (IOException e) {
               throw new RuntimeException(e);
          }
     }

     protected void startPollingServer(String orderId) {
          TimerTask task = () -> {
               try {
                    server.checkStatus(orderId).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
                    if (string == OrderStatus.Ready){
                         pickUpOrder();
                         timer.cancel();
                    }
               } catch (InterruptedException e){
                    e.printStackTrace();
               }
          };
          Timer timer = new Timer("Timer");
          long delay = 1000L;
          timer.schedule(task, delay);
     }

     protected void pickUpOrder() {
          while (order.isDone()) {
               System.out.println("Order is picked up.");
               break;
          }
     }

     public Order getOrder() {
          return order;
     }
}
