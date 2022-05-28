package Entity;

import Control.Controller;

public class Client extends AbstractOrderClient implements Runnable{
     private Order order;
     private Controller controller;
     private BoundedBuffer buffer;

     public Client(){

     }


    // @Override
     public void run() {
          while (order == null){
               buffer.recieveOrder()
          }
     }

    @Override
    public void submitOrder(Order order) {
          try {
               buffer.submitOrder(order);
          } catch (InterruptedException e){
               e.printStackTrace();
          }
    }

    @Override
    protected void startPollingServer(String orderId) {

    }

    @Override
    protected void pickUpOrder() {

    }
}
