package Entity;

import Control.AbstractOrderClient;
import Control.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {
     private Socket socket;
     private ObjectInputStream ois;
     private ObjectOutputStream oos;
     private User user; //Rätt
     private Controller controller; //Rätt

     public Client(String ipAdress, int port){
          try {
               socket = new Socket(ipAdress, port);
               oos = new ObjectOutputStream(socket.getOutputStream());
               ois = new ObjectInputStream(socket.getInputStream());
               controller = new Controller(); //Rätt
               user = new User(); //Rätt
             //  new Thread(this).start(); // Hmm...
          } catch (IOException e){
               e.printStackTrace();
          }
     }

    // @Override
     public void run() {
          //oos.write();
     }
     /**
      * Samma princip som submit order fast inte implementerade = egen metod
      * Ska aktiveras när "Order"-knappen trycks, skickar ett user object till server som har en order
      */
     public void order() throws InterruptedException {
         try {
             oos.writeObject(user);
             sendUpdates();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void sendUpdates() throws InterruptedException, IOException {
         while (user.getCurrentOrder().getStatus() != OrderStatus.Served){
             Random random = new Random();
             Thread.sleep(random.nextInt(3000));
             oos.writeObject("Update please");
         }
     }
    /*
    @Override
    public void submitOrder() {

    }

    @Override
    protected void startPollingServer(String orderId) {

    }

    @Override
    protected void pickUpOrder() {

    } */

    class InputCommunicator extends Thread {
          public void run(){
               while (!Thread.interrupted()) {
                   Object temp = null;
                   try {
                       temp = ois.readObject();
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
                   switch ((String) temp){
                       case "Recieved":
                           //GUI UPDATE
                           break;
                       case "Prepared":
                           //GUI UPDATE
                           break;
                       case "Ready":
                           //GUI UPDATE
                           break;
                       case "Served":
                           //GUI UPDATE
                           break;
                   }
               }
          }
     }

     /*class OutputCommunicator extends Thread {
          public void run(){
               while (!Thread.interrupted()){
                    //Nånting
               }
          }
     } */

    public static void main(String[] args) {
       new Client("127.0.0.1", 20002); //Rätt
    }
}
