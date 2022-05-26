package Control;

import Boundary.*;
import Entity.Order;
import Entity.OrderItem;

public class Controller {
     Order order = new Order(); //Rätt
     OrderItem orderItem = new OrderItem(); //Rätt
     RestaurantGUI gui; //Rätt


     public Controller(){
          this.gui = new RestaurantGUI(this); //Rätt
     }

     public void remove() {
          gui.clearOrder();
     }

     public void placeOrder() {
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
}
