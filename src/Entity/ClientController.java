package Entity;

import Boundary.RestaurantGUI;
import Control.ClientController;
import Control.ServerController;

import java.util.Timer;

public class ClientController extends AbstractOrderClient{
     private Timer timer;
     private ServerController server;
     private RestaurantGUI gui;
     private Order order;

     public ClientController(ServerController server) {
          this.server = server;
          gui = new RestaurantGUI(this);
          order = new Order();
     }

     @Override
     public void submitOrder(Order order) {

     }

     @Override
     protected void startPollingServer(String orderId) {

     }

     @Override
     protected void pickUpOrder() {

     }

     public void addToOrderList(String orderItem){

     }

     public void remove(){

     }
}
