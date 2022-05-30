package Entity;

import Boundary.RestaurantGUI;
import Control.Controller;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
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
     private Controller controller;

     public Client(Server server, Buffer buffer, RestaurantGUI gui, Controller controller) {
          this.server = server;
          this.buffer = buffer;
          this.gui = gui;
          this.controller = controller;
          order = new Order();
          timer = new Timer();
     }

     public void submitOrder() {
          if (!order.getOrderList().isEmpty()) {
               buffer.put(order);
               //server.receiveOrder(order).thenAccept(orderStatus -> gui.updateStatusLog(orderStatus.text));
               startPollingServer(order.getOrderID());
               orders.add(order);
               controller.remove();
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
     protected void startPollingServer(String orderID) {
          TimerTask task = new TimerTask() {
               @Override
               public void run() {
                    try {
                         OrderStatus orderStatus = server.checkStatus(orderID);
                         controller.updateStatusLog(String.valueOf(orderStatus));
                         if (orderStatus == OrderStatus.Ready){
                              pickUpOrder();

                         }
                    } catch (InterruptedException e){
                         e.printStackTrace();
                    }
               }
          };
          timer.scheduleAtFixedRate(task, 0, 750);
     }

     protected void pickUpOrder() {
          timer.cancel();
          gui.updateStatusLog("Order is picked up.");
     }

     public Order getOrder() {
          return order;
     }
}
