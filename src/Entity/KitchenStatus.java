package Entity;

public enum KitchenStatus {
    Received("Order received"),
    Rejected("Order rejected"), //Om alla kockar är upptagna
    NotFound("Order not found"),
    Cooking("Order is being prepared"),
    Ready("Order is ready"),
    Completed("Order is picked up");

    public final String text;

    KitchenStatus(String name){
        this.text = name;
    }
}
