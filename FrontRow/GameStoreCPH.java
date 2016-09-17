package FrontRow;

import FrontRow.Database.*;
import FrontRow.Model.*;
import FrontRow.views.CheckOutView;
import FrontRow.views.dialogs.AddSelectCustomerDialog;
import FrontRow.views.dialogs.GameTable;
import FrontRow.views.dialogs.LogInDialog;
import FrontRow.views.menus.MainMenuBar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by chill on 4/25/16.
 */
public class GameStoreCPH extends Application {

    public static ObservableList<Customer> fullCustomerList = FXCollections.observableArrayList();
    public ObservableList<Employee> fullEmployeeList = FXCollections.observableArrayList();
    public ObservableList<Cart> fullCartList = FXCollections.observableArrayList();

    public GameTable gameTable = new GameTable();
    // List of Games to be used in Cart Game List
    public static ObservableList<Game> cartGameList = FXCollections.observableArrayList();

    public static Scene neverInTheMain;


    private MainMenuBar menuBar;
    public static Stage mainStage;

    Button checkout = new Button("Check Out");


    public static Stage getMainStage() {
        return mainStage;
    }

    private Cart currentCart;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;

        menuBar = new MainMenuBar();
        Database DB = new Database();

        DB.connectDatabase();

        fullCustomerList.addAll(DBcustomer.readAllCustomers());
        fullEmployeeList.addAll(DBemployee.readAllEmployees());
        fullCartList.addAll(DBcart.readAllCarts());
        
        Debug.print("FULL CUSTOMER LIST: "+ fullCustomerList);
        Debug.print("FULL EMPLOYEE LIST: "+ fullEmployeeList);
        Debug.print("FULL CART LIST: "+ fullCartList);


        BorderPane root = new BorderPane();
        LogInDialog logInDialog = new LogInDialog();

        CheckOutView checkOutView = new CheckOutView();
        checkout.setDefaultButton(true);

        logInDialog.viewLogInScreen();

        if (logInDialog.getAuthorization()){
            root.setTop(menuBar.mainMenuBar());

            root.setCenter(gameTable.showGameTableView());

            //checkout button
            ButtonBar b1 = new ButtonBar();
            b1.getButtons().add(checkout);
            checkout.setAlignment(Pos.BOTTOM_RIGHT);
            root.setBottom(b1);

        addListeners();



            neverInTheMain = new Scene(root,1260,500);
            mainStage.setScene(neverInTheMain);

            mainStage.setTitle("RoManiacs");

            mainStage.show();

        }


    }

    public void updateData(){
        fullCustomerList.clear();
        fullEmployeeList.clear();
        fullCartList.clear();
        fullCustomerList.addAll(DBcustomer.readAllCustomers());
        fullEmployeeList.addAll(DBemployee.readAllEmployees());
        fullCartList.addAll(DBcart.readAllCarts());

        DBgame.readAllGames();
        DBcart.readAllCarts();
    }

    private void addListeners(){
        checkout.setOnAction(event -> {
            AddSelectCustomerDialog customerDialog = new AddSelectCustomerDialog();
            mainStage.setScene(customerDialog.showCustomerDialog());
        });

    }



    // Start DB Tests
    private static void employeeInsertToDBTest(){

        Employee e = new Employee();
        e.setFirst_name("Colin");
        e.setLast_name("Deleteme");
        e.setPassword("Cheers");

        DBemployee.insertEmployee(e);
    }

    private static void customerInsertToDBTest(){

        Customer c = new Customer();
        c.setFirst_name("Test");
        c.setLast_name("DeleteMe");

        DBcustomer.insertUpdateCustomer(0,c);
    }

    /*private static void cartInsertToDBTest(){

        Customer c = new Customer();
        c.setFirst_name("A");
        c.setLast_name("A");
        DBcustomer.insertCustomer(c);

        Employee e = new Employee();
        e.setFirst_name("B");
        e.setLast_name("B");
        e.setPassword("1234");
        DBemployee.insertEmployee(e);

        Cart ca = new Cart();
        ca.setCustomer_id(2);
        ca.setEmployee_id(1);

        DBcart.insertCart(ca);
    }*/

    // End DB Tests 
    public static void main(String[] args){launch(args);}

}
