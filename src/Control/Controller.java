package Control;

import Boundary.*;
import Entity.*;

public class Controller {
    // private Order order = new Order();
     private OrderItem orderItem = new OrderItem();
     private RestaurantGUI gui;
     private Server server;
     private Client client, client2;
     private TheInternet buffer;


     public Controller(){
          gui = new RestaurantGUI(this);
          buffer = new TheInternet();
          client = new Client(buffer);
          //client2 = new Client(buffer)
          server = new Server(buffer);
     }

     public void remove() {
          gui.clearOrder();
         // client.getOrder().clear();
     }

     public void placeOrder() throws InterruptedException {
          gui.clearOrder();
          gui.disableAllButtons();
          client.submitOrder();
     }

     public void addToOrderList(String string) {
          switch (string){
               case "sandwich" -> {
                    client.getOrder().addOrderItem(orderItem.sandwich());
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
