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
        System.out.println("Order added to buffer (orders)");
        orders.addLast(order);
        for (int i = 0; i < order.getOrderList().size(); i++) {
            System.out.println(orders.get(i).getOrderList().get(i).getName());

        }
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

