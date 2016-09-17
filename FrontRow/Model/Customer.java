package FrontRow.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.stream.Stream;

/**
 * Created by Mac on 25/04/2016.
 */
public class Customer {
    private SimpleIntegerProperty customer_id;
    private SimpleStringProperty first_name;
    private SimpleStringProperty last_name;

    public Customer(){}

    public Customer (String firstName, String lastName){
        this.first_name = new SimpleStringProperty(firstName);
        this.last_name = new SimpleStringProperty(lastName);
    }
    public Customer(int customer_id, String firstName, String lastName){
        this.customer_id = new SimpleIntegerProperty(customer_id);
        this.first_name = new SimpleStringProperty(firstName);
        this.last_name = new SimpleStringProperty(lastName);
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s", first_nameProperty().getValue(),
                last_nameProperty().getValue());
    }

    // Setters //

    public void setCustomer_id(int customer_id) {
        this.customer_id = new SimpleIntegerProperty(customer_id);
    }

    public void setFirst_name(String first_name) {
        this.first_name = new SimpleStringProperty(first_name);
    }

    public void setLast_name(String last_name) {
        this.last_name = new SimpleStringProperty(last_name);
    }

    // Getters //

    public int getCustomer_id() {
        return customer_id.get();
    }

    public SimpleIntegerProperty customer_idProperty() {
        return customer_id;
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public SimpleStringProperty first_nameProperty() {
        return first_name;
    }

    public String getLast_name() {
        return last_name.get();
    }

    public SimpleStringProperty last_nameProperty() {
        return last_name;
    }

}
