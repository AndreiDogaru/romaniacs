package FrontRow.views.dialogs;

import FrontRow.Database.DBemployee;
import FrontRow.Model.Debug;
import FrontRow.Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by chill on 4/25/16.
 */
public class LogInDialog {

    // TODO Create Log In Screen Here for employee to log in

    public Stage loginDialog;
    private boolean isAuthorized;

    private Label loginStatus = new Label("");
    private Label usernameLabel = new Label("Username");
    private Label passwordLabel = new Label("Password");
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button logInButton = new Button("Login");
    private Button closeButton = new Button("Exit");
    private ObservableList<Employee> allEmployees = FXCollections.observableArrayList();
    public static Employee currentEmployee;

    private void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public boolean getAuthorization(){
        return isAuthorized;
    }

    public void viewLogInScreen(){
        allEmployees.addAll(DBemployee.readAllEmployees());

        loginDialog = new Stage();

        loginDialog.setTitle("Welcome to RoManIacs");

        BorderPane bp = new BorderPane();
        bp.setCenter(dataFields());

        Scene scene = new Scene(bp, 350, 200);
        scene.getStylesheets().addAll("FrontRow/views/CSS/playstationStyle.css");
        loginDialog.setScene(scene);
        loginDialog.setTitle("Employee Login");

        addListeners();
        loginDialog.showAndWait();

    }

    private GridPane dataFields(){
        GridPane gp = new GridPane();
        gp.add(loginStatus, 0, 0, 2,1);
        gp.addColumn(0, usernameLabel, passwordLabel, closeButton);
        gp.addColumn(1, usernameField, passwordField, logInButton);
        gp.setAlignment(Pos.CENTER);
        logInButton.setDefaultButton(true);
        return gp;
    }

    private void addListeners(){
        logInButton.setOnAction(event -> {
           isAuthorized();
            if (isAuthorized){
                loginDialog.close();
            }
        });

        closeButton.setOnAction(event -> loginDialog.close());
    }

    private void isAuthorized(){
        currentEmployee = new Employee();
        for (int i = 0; i < allEmployees.size(); i++) {
            if (usernameField.getText().equalsIgnoreCase(allEmployees.get(i).getFirst_name())) {
                currentEmployee = allEmployees.get(i);
                Debug.print(currentEmployee.getFirst_name());
            } else {
                loginStatus.setText("Not a Valid Username");
            }
        }
        if (!currentEmployee.getPassword().equals(passwordField.getText())){
            loginStatus.setText("Not a Valid Password. Try Again!");
             setAuthorized(false);
        } else {
         setAuthorized(true);
        }
    }


}
