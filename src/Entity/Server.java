package Entity;

import Control.AbstractKitchenServer;
import Control.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class Server extends AbstractKitchenServer {
     private final Controller controller;
     private ServerSocket serverSocket;

     public Server(Controller controller, int port){
          this.controller = controller;
          try {
               serverSocket = new ServerSocket(port);
          } catch (IOException e){
               e.printStackTrace();
          }
     }

     @Override
     public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
          return null;
     }

     @Override
     protected void cook(Order order) {

     }

     private class Connection implements Runnable{
          @Override
          public void run(){
               Socket socket;
               while (true){
                    try {
                         socket = serverSocket.accept();
                         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                         new Thread(new ClientHandler(ois, oos, );

                    } catch (IOException e){
                         e.printStackTrace();
                    }
               }
          }
     }

     private class ClientHandler implements Runnable{
          private ObjectInputStream ois;
          private ObjectOutputStream oos;
          private User user;
          public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos, User user){
               this.oos = oos;
               this.ois = ois;
               this.user = user;
          }
          @Override
          public void run() {
               while (true){
                    //Nånting
               }
          }
     }
}
