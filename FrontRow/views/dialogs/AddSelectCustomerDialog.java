package FrontRow.views.dialogs;

import FrontRow.Database.DBcustomer;
import FrontRow.GameStoreCPH;
import FrontRow.Model.Customer;
import FrontRow.Model.Debug;
import FrontRow.views.CheckOutView;
import FrontRow.views.menus.MainMenuBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.security.PublicKey;

/**
 * Created by chill on 4/25/16.
 */
public class AddSelectCustomerDialog {

    // Requirements //
    // TODO Search Field (Search Customer Table)

    private TextField customerSearchField;
    private TableView<Customer> customerTableView;
    private TableColumn<Customer, String> firstNameCol;
    private TableColumn<Customer, String> lastNameCol;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Customer> searchList = FXCollections.observableArrayList();
    public boolean isSelectUserClicked = true;

    private TextField firstNameField;
    private TextField lastNameField;

    private Button addButton;
    public static Button okButton;
    public Button cancelButton;
    private Button viewReceipts;

    public static String customerName = "";
    public static Customer selectedCustomer;
    private DBcustomer dBcustomer;



    public boolean isSelectUserClicked() {
        return isSelectUserClicked;
    }

    public void setSelectUserClicked(boolean selectUserClicked) {
        isSelectUserClicked = selectUserClicked;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    private void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public Scene showCustomerDialog(){
        selectedCustomer = new Customer();
        MainMenuBar menuBar = new MainMenuBar();
        customerSearchField = new TextField();
        customerSearchField.setMinWidth(300);
        customerSearchField.setStyle("-fx-text-fill: lawngreen;\n" +
                "    -fx-background-color: black;");
        BorderPane bp = new BorderPane();

        bp.setTop(menuBar.mainMenuBar());
        bp.setCenter(centerView());
        bp.setBottom(setBottom());

        addListeners();
        customerTableView.getStylesheets().add("FrontRow/views/CSS/playstationStyle.css");
        return new Scene(bp, 500, 400);

    }

    private BorderPane centerView(){
        BorderPane bp = new BorderPane();
        bp.setTop(customerSearchField);
        bp.setCenter(showCustomerTableView());

        return bp;
    }
    private TableView showCustomerTableView(){

        customerList.addAll(GameStoreCPH.fullCustomerList);
        customerTableView = new TableView<>(customerList);

        firstNameCol = new TableColumn<>("First Name");
        lastNameCol = new TableColumn<>("Last Name");

        firstNameCol.setCellValueFactory(e -> e.getValue().first_nameProperty());
        lastNameCol.setCellValueFactory(e -> e.getValue().last_nameProperty());

        firstNameCol.setPrefWidth(250);
        lastNameCol.setPrefWidth(250);

        customerTableView.getColumns().addAll(firstNameCol, lastNameCol);


        customerTableView.autosize();
        return customerTableView;
    }

    private VBox setBottom(){
        VBox bottomLayout = new VBox();

        HBox addUserRow = new HBox();
        addButton = new Button("Add Customer");
        firstNameField = new TextField();
        lastNameField = new TextField();
        addUserRow.getChildren().addAll(firstNameField, lastNameField, addButton);

        HBox bottomButtons = new HBox();
        okButton = new Button("Select User");
        cancelButton = new Button("Back");
        viewReceipts = new Button("View Receipts");
        bottomButtons.getChildren().addAll(viewReceipts, cancelButton, okButton);
        bottomButtons.alignmentProperty().setValue(Pos.BOTTOM_RIGHT);

        bottomLayout.getChildren().addAll(addUserRow, bottomButtons);
        bottomLayout.setSpacing(50);



        return bottomLayout;
    }

    private void addListeners(){

        searchFieldListeners();


        viewReceipts.setOnAction(event -> {
            SelectCustomerReceipts customerReceipts = new SelectCustomerReceipts();
            Debug.print("Customer Selected: "+ selectedCustomer);
            GameStoreCPH.getMainStage().setScene(customerReceipts.showSelectCustomerReceipts(selectedCustomer));
        });

        addButton.setOnAction(event -> {
            DBcustomer.insertUpdateCustomer(0, getValidCustomer());
            customerList.add(getValidCustomer());
            GameStoreCPH main = new GameStoreCPH();
            main.updateData();
        });

        cancelButton.setOnAction(event -> {
            GameStoreCPH.getMainStage().setScene(GameStoreCPH.neverInTheMain);
        } );

        customerTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setSelectedCustomer(newValue);
            okButton.setOnAction(event -> {
                GameStoreCPH main = new GameStoreCPH();
                main.updateData();

                customerName=selectedCustomer.getCustomer_id()+"";
                Debug.print(getSelectedCustomer()+ " was Selected ");
                CheckOutView checkOutView = new CheckOutView();
                checkOutView.showChechOutView();

            });
        });


    }

    private Customer getValidCustomer(){
        Customer customer = new Customer();
        if (firstNameField.getText().equalsIgnoreCase("") || lastNameField.getText().equalsIgnoreCase("")){
            Debug.print("Invalid Entry");
        } else {
            customer.setFirst_name(firstNameField.getText());
            customer.setLast_name(lastNameField.getText());
        }
        return customer;
    }

    private void searchFieldListeners() {
        // 0. Initialize the columns.
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().first_nameProperty());
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().last_nameProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Customer> filteredData = new FilteredList<>(customerList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        customerSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getFirst_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getLast_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerTableView.setItems(sortedData);
    }
}
