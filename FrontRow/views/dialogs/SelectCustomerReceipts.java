package FrontRow.views.dialogs;

import FrontRow.Database.DBcart;
import FrontRow.Model.Cart;
import FrontRow.Model.Customer;
import FrontRow.Model.Debug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by chill on 4/28/16.
 */
public class SelectCustomerReceipts {

    public final ObservableList<Cart> customerOrderHistory = FXCollections.observableArrayList();


    private TableView<Cart> cartTableView;
    private TableColumn<Cart, Integer> cartIdCol;
    private TableColumn<Cart, Double> totalPrice;
    private Button viewButton, backButton;

    private static Cart selectedCart;

    public static Cart getSelectedCart() {
        return selectedCart;
    }

    public  void setSelectedCart(Cart selectedCart) {
        this.selectedCart = selectedCart;
    }

    public Scene showSelectCustomerReceipts(Customer customer){
        BorderPane bp = new BorderPane();
        viewButton = new Button("View");
        backButton = new Button("Back");
        viewButton.setAlignment(Pos.BOTTOM_RIGHT);

        HBox buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(backButton, viewButton);

        bp.setCenter(createCartTableView(customer));
        bp.setBottom(buttonLayout);

        addListeners();
        Scene scene = new Scene(bp, 500, 500);
        return scene;
    }

    private TableView createCartTableView(Customer customer){
        // Set Customer OrderHistory
        customerOrderHistory.addAll(DBcart.viewCartData(customer.getCustomer_id()));

        cartTableView = new TableView<>(customerOrderHistory);
        cartIdCol = new TableColumn<>("ID");
        cartIdCol.setCellValueFactory(e -> e.getValue().cart_idProperty().asObject());

        totalPrice = new TableColumn<>("Total");
        totalPrice.setCellValueFactory(e -> e.getValue().cartTotalProperty().asObject());

        cartTableView.getColumns().addAll(cartIdCol, totalPrice);

        return cartTableView;

    }

    private void addListeners(){
        cartTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Cart c = new Cart();
            Debug.print(newValue+ " cart ID");


            c.setCart_id(newValue.getCart_id());
            setSelectedCart(c);
            Debug.print(c.getCart_id()+ " Selected Cart ID");
            viewButton.setOnAction(event -> {
                RecipetView r = new RecipetView();
                r.reciept();

            });
        });


    }



}
