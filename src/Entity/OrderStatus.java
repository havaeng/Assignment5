package Entity;

public enum OrderStatus {
    NotSent("Not sent to the server."),
    Received("Received!"),
    BeingPrepared("Preparing..."),
    Ready("Ready!"),
    Served("Served."),
    NotFound("Not found");

    public final String text;

    OrderStatus(String name){
        this.text = name;
    }
}
