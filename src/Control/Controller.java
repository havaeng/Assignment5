package Control;

import Boundary.*;
import Entity.Client;
import Entity.Order;
import Entity.OrderItem;
import Entity.Server;

public class Controller {
    // private Order order = new Order();
     private OrderItem orderItem = new OrderItem();
     private RestaurantGUI gui;
     private Server server;
     private Client client;


     public Controller(){
        //  client = new client(); // Hur startar man på clienten i runnable?
         // server = new server(); // Hur startar man på servern i runnable?
          this.gui = new RestaurantGUI(this);
     }

     public void remove() {
          gui.clearOrder();
         // client.getOrder().clear();
     }

     public void placeOrder() throws InterruptedException {
          gui.clearOrder();
          //gui.disableAllButtons();
      //    client.placeOrder();
     }

     public void addToOrderList(String string) {
          switch (string){
               case "sandwich" -> {
          //          client.getOrder().addOrderItem(orderItem.sandwich());
                    gui.updateOrderCart("Sandwich");
               }
               case "borscht" -> {
          //          client.getOrder().addOrderItem(orderItem.borscht());
                    gui.updateOrderCart("Borscht");
               }
               case "coffee" -> {
                //    client.getOrder().addOrderItem(orderItem.coffee());
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
