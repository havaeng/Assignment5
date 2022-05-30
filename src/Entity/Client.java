package Entity;

import Boundary.RestaurantGUI;
import Control.Controller;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Client extends AbstractOrderClient{
     private Timer timer;
     private Server server;
     private RestaurantGUI gui;
     private Order order;
     private List<Order> orders = new ArrayList<>();
     private OrderItem orderItem = new OrderItem();
     private Buffer buffer;

     public Client(Server server, Controller controller, Buffer buffer) {
          this.server = server;
          this.buffer = buffer;
          gui = new RestaurantGUI(controller);
          order = new Order();
     }

     public void submitOrder() {
          if (!order.getOrderList().isEmpty()) {
               buffer.put(order);
               //server.receiveOrder(order).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
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
     }

     @Override
     protected void startPollingServer(String orderId) {
          /*
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

           */
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
