package Control;

import Boundary.*;
import Entity.Client;
import Entity.Order;
import Entity.OrderItem;

public class Controller {
     private Order order = new Order();
     private OrderItem orderItem = new OrderItem();
     private RestaurantGUI gui;
     private Client client;


     public Controller(Client client){
          this.client = client;
          client.setController(this);
          this.gui = new RestaurantGUI(this);
     }

     public void remove() {
          gui.clearOrder();
          order.clear();
     }

     public void placeOrder() throws InterruptedException {
          gui.clearOrder();
          gui.disableAllButtons();
          client.placeOrder();
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

     public void updateOrderCart(String string){
          gui.updateOrderCart(string);
     }

     public void updateStatusLog(String string){
          gui.updateStatusLog(string);
     }

     public void enableAllButtons() {
          gui.enableAllButtons();
     }
}
