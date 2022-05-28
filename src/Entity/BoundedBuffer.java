package Entity;
import Control.Controller;
import Entity.Order;
import Entity.Server;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer {
    private LinkedList<Order> orders;
    private Controller controller;

    public BoundedBuffer(Controller controller) {
        this.controller = controller;
        orders = new LinkedList<>();
    }

    public void submitOrder(Order order) throws InterruptedException {
        orders.addLast(order);
    }

    public Order recieveOrder(Server server) throws InterruptedException {
        Order order = orders.removeFirst();
        return order;
    }
}

