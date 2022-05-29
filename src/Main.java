import Entity.ClientController;
import Entity.ServerController;

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();
        new ClientController(serverController);
    }
}
