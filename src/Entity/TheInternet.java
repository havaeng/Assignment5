package Entity;

import java.util.LinkedList;

public class TheInternet /*Buffer*/ {
    private LinkedList<Order> orders;
    private LinkedList<String> requests;

    public TheInternet() {
        orders = new LinkedList<>();
        requests = new LinkedList<>();
    }

    public void submitOrder(Order order) throws InterruptedException {
        orders.addLast(order);
    }

    public Order receiveOrder() throws InterruptedException {
        Order order = orders.removeFirst();
        return order;
    }

    public void sendRequest(String string){
        requests.addLast(string);
    }

    public String getRequest(){
        return requests.removeFirst();
    }
}

