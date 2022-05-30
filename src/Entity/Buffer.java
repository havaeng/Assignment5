package Entity;

import java.util.LinkedList;

public class Buffer {
     private LinkedList<Order> buffer = new LinkedList<>();

     public synchronized void put(Order order) {
          buffer.addLast(order);
          notifyAll();
     }

     public synchronized Order get() throws InterruptedException {
          while(buffer.isEmpty()){
               wait();
          }
          return buffer.removeFirst();
     }

     public synchronized int size(){
          return buffer.size();
     }

     public synchronized boolean isEmpty(){
          return buffer.isEmpty();
     }
}
