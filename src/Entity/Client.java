package Entity;

public class Client {
     private Order order;
     private TheInternet buffer;

     public Client(TheInternet buffer) {
          this.buffer = buffer;
          order = new Order();
     }

     public void submitOrder() {
          try {
               if (!order.getOrderList().isEmpty()) {
                    buffer.submitOrder(order);
                    System.out.println("Order submitted.");
               }
          } catch (InterruptedException e) {
               e.printStackTrace();
          }
     }

     protected void startPollingServer(String orderId) {

     }

     protected void pickUpOrder() {

     }

     public Order getOrder() {
          return order;
     }
}
