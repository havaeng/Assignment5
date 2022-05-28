package Control;

import Boundary.*;
import Entity.Client;
import Entity.Order;
import Entity.OrderItem;

public class Controller {
    // private Order order = new Order();
     private OrderItem orderItem = new OrderItem();
     private RestaurantGUI gui;
     private Client client;


     public Controller(){
          client = new Client("127.0.0.1", 20004, this);
          this.gui = new RestaurantGUI(this);
     }

     public void remove() {
          gui.clearOrder();
          client.getOrder().clear();
     }

     public void placeOrder() throws InterruptedException {
          gui.clearOrder();
          //gui.disableAllButtons();
          client.placeOrder();
     }

     public void addToOrderList(String string) {
          switch (string){
               case "sandwich" -> {
                    client.getOrder().addOrderItem(orderItem.sandwich());
                    gui.updateOrderCart("Sandwich");
               }
               case "borscht" -> {
                    client.getOrder().addOrderItem(orderItem.borscht());
                    gui.updateOrderCart("Borscht");
               }
               case "coffee" -> {
                    client.getOrder().addOrderItem(orderItem.coffee());
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
