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
        System.out.println("Order sent to the Internet");
        orders.addLast(order);
        for (int i = 0; i < order.getOrderList().size(); i++) {
            System.out.println("Item no. " + (i + 1) + " in the order: " + orders.getLast().getOrderList().get(i).getName());
        }
    }

    public Order receiveOrder() throws InterruptedException {
        if (!orders.isEmpty()) {
            Order order = orders.removeFirst();
            System.out.println("Order ´´consumed´´ from the Internet");
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

