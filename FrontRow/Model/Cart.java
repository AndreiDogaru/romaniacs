package FrontRow.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Mac on 25/04/2016.
 */
public class Cart {
    private SimpleIntegerProperty cart_id;
    private SimpleIntegerProperty employee_id;
    private SimpleIntegerProperty customer_id;
    private SimpleDoubleProperty cartTotal;


    public Cart(){}

    public Cart(int cart_id, int employee_id, int customer_id){
        this.cart_id = new SimpleIntegerProperty(cart_id);
        this.employee_id = new SimpleIntegerProperty(employee_id);
        this.customer_id = new SimpleIntegerProperty(customer_id);
    }

    public Cart(int cart_id, double totalPrice){
        this.cart_id = new SimpleIntegerProperty(cart_id);
        this.cartTotal = new SimpleDoubleProperty(totalPrice);

    }

    @Override
    public String toString() {
//        return String.format("Receipt ID %s, Employee ID: %s, Customer ID: %s  \n",
//                cart_idProperty().getValue(), employee_idProperty().getValue(),
//                customer_idProperty().getValue());

        return String.format("Receipt ID %s", cart_idProperty().getValue());
    }

    // Setters //
    
    public void setCart_id(int cart_id) {
        this.cart_id = new SimpleIntegerProperty(cart_id);
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = new SimpleIntegerProperty(employee_id);
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = new SimpleIntegerProperty(customer_id);
    }

    public void setCartTotal(double cartTotal) {
        this.cartTotal = new SimpleDoubleProperty(cartTotal);
    }

    // Getters //

    public int getCart_id() {
        return cart_id.get();
    }

    public SimpleIntegerProperty cart_idProperty() {
        return cart_id;
    }

    public int getEmployee_id() {
        return employee_id.get();
    }

    public SimpleIntegerProperty employee_idProperty() {
        return employee_id;
    }

    public int getCustomer_id() {
        return customer_id.get();
    }

    public SimpleIntegerProperty customer_idProperty() {
        return customer_id;
    }

    public double getCartTotal() {
        return cartTotal.get();
    }

    public SimpleDoubleProperty cartTotalProperty() {
        return cartTotal;
    }
}
