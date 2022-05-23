package Entity;

import Control.AbstractOrderClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AbstractOrderClient implements Runnable {
     private Socket socket;
     private ObjectInputStream ois;
     private ObjectOutputStream oos;

     public Client(String ipAdress, int port){
          try {
               socket = new Socket(ipAdress, port);
               oos = new ObjectOutputStream(socket.getOutputStream());
               ois = new ObjectInputStream(socket.getInputStream());
               new Thread(this).start();
          } catch (IOException e){
               e.printStackTrace();
          }
     }

     @Override
     public void run() {
          //oos.write();
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

     class InputCommunicator extends Thread {
          public void run(){
               while (!Thread.interrupted()) {
                    //Nånting
               }
          }
     }

     class OutputCommunicator extends Thread {
          public void run(){
               while (!Thread.interrupted()){
                    //Nånting
               }
          }
     }
}
