package Control;

import Boundary.*;
import Entity.Order;
import Entity.OrderItem;
import Entity.Server;

public class Controller {
     Order order = new Order();
     OrderItem orderItem = new OrderItem();
     RestaurantGUI gui;


     public Controller(){
          this.gui = new RestaurantGUI(this);
          new Server(this, 20000);
     }

     public void remove() {
     }

     public void order() {
     }

     public void addToOrderList(String jButtonName) {
          switch (jButtonName){
               case "sandwich" -> {
                    order.addOrderItem(orderItem.sandwich());
               }
               case "borscht" -> {
                    order.addOrderItem(orderItem.borscht());
               }
               case "coffee" -> {
                    order.addOrderItem(orderItem.coffee());
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
