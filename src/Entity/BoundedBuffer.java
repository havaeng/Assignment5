package Entity;
import Control.Controller;
import Entity.Order;
import Entity.Server;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer {
    private LinkedList<Order> orders;
    private Controller controller;

    private Semaphore semProducer; // Kolla in!
    private Semaphore semConsumer;
    private Semaphore wholeBuffMutex;

    public BoundedBuffer(Controller controller) {
        this.controller = controller;
        orders = new LinkedList<>();
        semProducer = new Semaphore(20);
        semConsumer = new Semaphore(0);
        wholeBuffMutex = new Semaphore(1);
    }

    public void submitOrder(Order order) throws InterruptedException
    {
        semProducer.acquire();
        wholeBuffMutex.acquire();

        orders.addLast(order);
        semConsumer.release();
        wholeBuffMutex.release();
    }

    public Order recieveOrder(Server server) throws InterruptedException
    {
        semConsumer.acquire();
        wholeBuffMutex.acquire();

        Order order = orders.removeFirst();
        semProducer.release();
        wholeBuffMutex.release();
        return order;
    }
}

