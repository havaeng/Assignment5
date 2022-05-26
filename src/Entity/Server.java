package Entity;

import Control.AbstractKitchenServer;
import Control.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Server extends AbstractKitchenServer {
     private final Controller controller;
     private ServerSocket serverSocket;
     private List<User> userList;

     public Server(Controller controller, int port) {
          this.controller = controller;
          try {
               serverSocket = new ServerSocket(port);
          } catch (IOException e) {
               e.printStackTrace();
          }
          new Thread(new Connection()).start();
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

     private class Connection implements Runnable {
          @Override
          public void run() {
               Socket socket;
               while (true) {
                    try {
                         socket = serverSocket.accept();
                         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                         new Thread(new ClientHandler(ois, oos)).start();

                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
          }
     }

     private class ClientHandler implements Runnable {
          private ObjectInputStream ois;
          private ObjectOutputStream oos;
          private User user;

          public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos) {
               this.oos = oos;
               this.ois = ois;
               try {
                    Object user = ois.readObject();
                    this.user = (User) user;
               } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
               }
               new Thread(new InputHandler()).start();
               new OutputHandler();
          }

          @Override
          public void run() {
               while (true) {
                    //Nånting
               }
          }

          public void setUser(User user) {
               this.user = user;
          }

          class InputHandler implements Runnable {
               @Override
               public void run() {
                    while (!Thread.interrupted()) {
                         try {
                              User user = (User) ois.readObject();
                              setUser(user);
                         } catch (IOException | ClassNotFoundException e) {
                              e.printStackTrace();
                         }
                    }
               }
          }

          class OutputHandler {
               public void send(String string) {
                    try {
                         oos.writeObject(string);
                         oos.flush();
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
          }
     }
}
