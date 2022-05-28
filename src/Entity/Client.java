package Entity;

import Control.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Client extends AbstractOrderClient implements Runnable{
     private Order order;
     private Controller controller;

     public Client(){

     }


    // @Override
     public void run() {
     }

    @Override
    public void submitOrder() {

    }

    @Override
    protected void startPollingServer(String orderId) {

    }

    @Override
    protected void pickUpOrder() {

    }
}
