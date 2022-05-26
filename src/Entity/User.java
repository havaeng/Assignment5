package Entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 4L;
    private Long userId = serialVersionUID;
    private Order currentOrder;

    public User(){
        this.currentOrder = null;
    }

    public User(Order currentOrder){
        this.currentOrder = currentOrder;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", currentOrder=" + currentOrder +
                '}';
    }
}
