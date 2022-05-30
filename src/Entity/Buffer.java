package Entity;

import java.util.LinkedList;

public class Buffer {
     private LinkedList<Order> buffer = new LinkedList<>();

     public void put(Order order){
          buffer.addLast(order);
          notifyAll();
     }

     public Order get() throws InterruptedException {
          while(buffer.isEmpty()){
               wait();
          }
          return buffer.removeFirst();
     }

     public int size(){
          return buffer.size();
     }

     public boolean isEmpty(){
          return buffer.isEmpty();
     }
}
