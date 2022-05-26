package Control;

import Boundary.*;
import Entity.Server;

public class Controller {
     public Controller(){
          new GenericRestaurantForm(this);
          new Server(this, 20000);
     }
}
