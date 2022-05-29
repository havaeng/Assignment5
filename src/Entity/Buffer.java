package Entity;

import java.util.LinkedList;

public class Buffer /*Buffer*/ {
    private LinkedList<Order> orders;
    private LinkedList<String> requests;

    public Buffer() {
        orders = new LinkedList<>();
        requests = new LinkedList<>();
    }


    public Order receiveOrder() throws InterruptedException {
        if (!orders.isEmpty()) {
            Order order = orders.removeFirst();
            System.out.println("Order removed from buffer (orders)");
            return order;
        }
        return null;
    }

    public void sendRequest(String string){
        requests.addLast(string);
    }

    public String getRequest(){
        return requests.removeFirst();
    }
}

