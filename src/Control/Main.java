package Control;

import Entity.ClientController;

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();
        new ClientController(serverController);
    }
}
