package Control;

import Boundary.*;

public class Controller {
     GenericRestaurantForm gui = new GenericRestaurantForm(this);

     public Controller(){
          gui.start();
     }
}
