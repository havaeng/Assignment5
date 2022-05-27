package Entity;

import java.io.Serializable;
import java.util.UUID;

public class OrderItem implements Serializable {
    private String itemID;
    private String name;
    private String description;
    private float cost;

    public OrderItem(){

    }

    public OrderItem(String name, String desc, float cost) {
        this.itemID = UUID.randomUUID().toString();
        this.name = name;
        this.description = desc;
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemID() {
        return itemID;
    }

    public OrderItem sandwich(){
        return new OrderItem("Sandwich", "Bread, meat, cheese, salad, vegetables, sauce", 23);
    }

    public OrderItem coffee(){
        return new OrderItem("Coffee", "Hot, black, good", 18);
    }

    public OrderItem borscht(){
        return new OrderItem("Borscht", "Beetroot, cabbage, potato, beef", 84);
    }

}