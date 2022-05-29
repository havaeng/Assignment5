package Entity;

import Boundary.RestaurantGUI;
import Control.ClientController;
import Control.ServerController;

import java.util.Timer;

public class ClientController extends AbstractOrderClient{
     private Timer timer;
     private ServerController server;
     private RestaurantGUI gui;

     public ClientController(ServerController server) {
          this.server = server;
          gui = new RestaurantGUI(this);
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
}
