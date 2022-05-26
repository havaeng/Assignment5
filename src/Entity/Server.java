package Entity;

import Control.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Server{
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
          private User user = null;

          public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos) {
               this.oos = oos;
               this.ois = ois;
               new Thread(this).start();
          }

          /**
           * 1. Försöker hämta en första order, lägger till usern som har order som instans
           * 2. När det är fixat startas inputHandler och outputHandler
           * Inputhandler will hämta fler ordrar/status req från användaren
           */
          @Override
          public void run() {
               while (user == null) {
                    Object user;
                    try {
                         user = ois.readObject();
                         this.user = (User) user;
                    } catch (IOException e) {
                         e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                         e.printStackTrace();
                    }
                    try {
                         recieveOrder();
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
               new Thread(new InputHandler()).start();
          }

          private void recieveOrder() throws IOException {
               System.out.println("Successfully stored the user/order!");
               this.user.getCurrentOrder().setStatus(OrderStatus.Received);
               statusInfo(user.getCurrentOrder().getStatus());
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(5000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               cook();
          }

          private void cook() throws IOException {
               this.user.getCurrentOrder().setStatus(OrderStatus.BeingPrepared);
               statusInfo(user.getCurrentOrder().getStatus());
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(6000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               serveOrder();
          }

          private void serveOrder() throws IOException {
               this.user.getCurrentOrder().setStatus(OrderStatus.Ready);
               statusInfo(user.getCurrentOrder().getStatus());
          }

          public void statusInfo(OrderStatus status) throws IOException { //Vill vi ha try catch?
               String string;
               switch (status){
                    case Received -> {
                         string = "You order " + user.getCurrentOrder().getOrderID() + " is recived!";
                         oos.writeObject(string);
                    }
                    case BeingPrepared -> {
                         string = "Your order " + user.getCurrentOrder().getOrderID() + " is being prepared!";
                         oos.writeObject(string);
                    }
                    case Ready -> {
                         string = "Your order " + user.getCurrentOrder().getOrderID() + " is ready!";
                         oos.writeObject(string);
                    }
                    case Served -> {
                         string = "Your picked up order " + user.getCurrentOrder().getOrderID();
                         oos.writeObject(string);
                    }
               }
          }

          public void setUser(User user) {
               this.user = user;
          }

          private class InputHandler implements Runnable {
               @Override
               public void run() {
                    while (!Thread.interrupted()) {
                         try {
                              Object temp = ois.readObject();
                              if(temp instanceof User) {
                                   setUser((User) temp);
                                   recieveOrder();
                              }
                              else if(temp instanceof String){
                                   statusInfo(user.getCurrentOrder().getStatus());
                              }
                         } catch (IOException | ClassNotFoundException e) {
                              e.printStackTrace();
                         }
                    }
               }
          }
     }
}
