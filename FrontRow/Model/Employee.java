package FrontRow.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mac on 25/04/2016.
 */
public class Employee {

    private SimpleIntegerProperty employee_id;
    private SimpleStringProperty first_name;
    private SimpleStringProperty last_name;
    private SimpleStringProperty password;

    public Employee(){}
    public Employee(int employee_id, String first_name, 
                    String last_name, String password) {
        this.employee_id = new SimpleIntegerProperty(employee_id);
        this.first_name = new SimpleStringProperty(first_name);
        this.last_name = new SimpleStringProperty(last_name);
        this.password = new SimpleStringProperty(password);
    }

    //SETTERS

    public void setEmployee_id(int employee_id) {
        this.employee_id = new SimpleIntegerProperty(employee_id);
    }

    public void setFirst_name(String first_name) {
        this.first_name = new SimpleStringProperty(first_name);
    }

    public void setLast_name(String last_name) {
        this.last_name = new SimpleStringProperty(last_name);
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }
    
    //GETTERS

    public int getEmployee_id() {
        return employee_id.get();
    }

    public SimpleIntegerProperty employee_idProperty() {
        return employee_id;
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

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String toString(){
        return String.format("Name: %s, %s, Password: %s \n", first_nameProperty().getValue(),
                last_nameProperty().getValue(),passwordProperty().getValue());
    }
}
