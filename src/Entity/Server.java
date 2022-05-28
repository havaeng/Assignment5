package Entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class Server {
     private ServerSocket serverSocket;
     private List<User> userList;
     private ExecutorService threadPool;
     private Map<String, Order> orderMap = new HashMap<>();

     public Server(int port) {
          try {
               serverSocket = new ServerSocket(port);
          } catch (IOException e) {
               e.printStackTrace();
          }
          threadPool = Executors.newFixedThreadPool(5);
          new Thread(new Connection()).start();
     }

     private class Connection implements Runnable {
          @Override
          public void run() {
               Socket socket;
               while (true) {
                    try {
                         socket = serverSocket.accept();
                         new Thread(new ClientHandler(socket)).start();

                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
          }
     }

     private class ClientHandler implements Runnable {
          private ObjectInputStream ois;
          private ObjectOutputStream oos;
          private Order order = null;

          /**
           * Listens for a user sent by Client
           */
          public ClientHandler(Socket socket) {
               try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
               } catch (IOException e) {
                    e.printStackTrace();
               }
               new Thread(this).start();
          }

          /**
           * 1. Försöker hämta en första order, lägger till usern som har order som instans
           * 2. När det är fixat startas inputHandler och outputHandler
           * Inputhandler will hämta fler ordrar/status req från användaren
           */
          @Override
          public synchronized void run() {
               while (this.order == null) {
                    try {
                         Object temp = ois.readObject();
                         this.order = (Order) temp;
                    } catch (IOException | ClassNotFoundException e) {
                         e.printStackTrace();
                    }
               }
               acceptOrder(order);
               new Thread(new InputHandler()).start();
          }

          private void acceptOrder(Order order){
               orderMap.put(order.getOrderID(), order);
               threadPool.submit(receiveOrder(order));
          }

          private Runnable receiveOrder(Order order){
               Runnable receiveOrder = () -> {
                    try {
                         Random random = new Random();
                         Thread.sleep(random.nextInt(1000,5000));
                    } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                    }
                    order.setStatus(OrderStatus.Received);
                    try {
                         cook(order);
                    } catch (IOException e) {
                         throw new RuntimeException(e);
                    }
               };
               return receiveOrder;
          }

          private void cook(Order order) throws IOException {
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000,5000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               this.order.setStatus(OrderStatus.BeingPrepared);
               statusInfo(order.getStatus());
               serveOrder(order);
          }

          private void serveOrder(Order order) throws IOException {
               try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000,5000));
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
               this.order.setStatus(OrderStatus.Ready);
               statusInfo(order.getStatus());
          }

          public void statusInfo(OrderStatus status) throws IOException { //Vill vi ha try catch?
               String string;
               switch (status){
                    case Received -> {
                         string = "Recieved";//"You order " + user.getCurrentOrder().getOrderID() + " is recived!";
                         oos.writeObject(string);
                    }
                    case BeingPrepared -> {
                         string = "Prepared";//"Your order " + user.getCurrentOrder().getOrderID() + " is being prepared!";
                         oos.writeObject(string);
                    }
                    case Ready -> {
                         string = "Ready";//"Your order " + user.getCurrentOrder().getOrderID() + " is ready!";
                         oos.writeObject(string);
                    }
                    case Served -> {
                         string = "Served";//"Your picked up order " + user.getCurrentOrder().getOrderID();
                         oos.writeObject(string);
                    }
               }
          }

          public void setOrder(Order order) {
               this.order = order;
          }

          private class InputHandler implements Runnable {
               @Override
               public synchronized void run() {
                    while (!Thread.interrupted()) {
                         try {
                              Object temp = ois.readObject();
                              if(temp instanceof User) {
                                   setOrder((Order) temp);
                                   acceptOrder(order);
                              }
                              else if(temp instanceof String){
                                   statusInfo(order.getStatus());
                              }
                         } catch (IOException | ClassNotFoundException e) {
                              e.printStackTrace();
                         }
                    }
               }
          }
     }
}
