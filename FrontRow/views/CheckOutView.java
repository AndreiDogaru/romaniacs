package FrontRow.views;

import FrontRow.Database.DBcart;
import FrontRow.Database.DBemployee;
import FrontRow.Database.DBgame;
import FrontRow.Database.Database;
import FrontRow.GameStoreCPH;
import FrontRow.Model.Cart;
import FrontRow.Model.Debug;
import FrontRow.Model.Game;
import FrontRow.views.dialogs.AddSelectCustomerDialog;
import FrontRow.views.dialogs.LogInDialog;
import FrontRow.views.dialogs.RecipetView;
import FrontRow.views.dialogs.SelectCustomerReceipts;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Doggy on 4/27/16.
 */
public class CheckOutView {

    private BorderPane root = new BorderPane();
    private TableView<Game> table;

    private Label salesMan, customer, totalLabel, valueLabel, currencyLabel, salesManValue, customerValue;
    private Button cancelButton, buyButton;
    public Stage checkOutStage;
    public Scene checkOutScene;

    LogInDialog l1;
    AddSelectCustomerDialog customerDialog;


    public void showChechOutView(){


        table = new TableView<>();
        table.itemsProperty().setValue(GameStoreCPH.cartGameList);

        TableColumn<Game, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Game, Number> priceColumn = new TableColumn<>("Price");
        TableColumn<Game, String> platformColumn = new TableColumn<>("Platform");
        TableColumn<Game, Number> qtyColumn = new TableColumn<>("Quantity");
        table.getColumns().addAll(nameColumn,priceColumn,platformColumn,qtyColumn);

        nameColumn.setCellValueFactory(e -> e.getValue().game_nameProperty());
        priceColumn.setCellValueFactory(e -> e.getValue().priceProperty());
        platformColumn.setCellValueFactory(e -> e.getValue().platformProperty());
        qtyColumn.setCellValueFactory(e -> e.getValue().qtyProperty());

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        platformColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        qtyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        Region reg1 = new Region();
        reg1.setPrefWidth(380);
        salesMan = new Label("Salesman Id: ");
        customer = new Label("Customer Id: ");

        salesManValue=new Label();
        customerValue = new Label();

        HBox salesManHBox = new HBox(salesMan,salesManValue);
        HBox customerHBox = new HBox(customer,customerValue);
        VBox idVBox = new VBox(5,salesManHBox,customerHBox);
        HBox idHBox = new HBox(10,reg1,idVBox);


        l1 = new LogInDialog();
        salesManValue.setText(l1.currentEmployee.getEmployee_id()+"");
        customerDialog = new AddSelectCustomerDialog();
        customerValue.setText(customerDialog.customerName);


        cancelButton = new Button("Exit");
        cancelButton.setOnAction(e -> {
            checkOutStage.close();
            GameStoreCPH.mainStage.setScene(GameStoreCPH.neverInTheMain);
        });
        buyButton = new Button("Buy");
        buyButton.setOnAction(e -> {
            GameStoreCPH main = new GameStoreCPH();
            finishTransaction();
            updateInventory();
            main.updateData();
            GameStoreCPH.cartGameList.clear();

            checkOutStage.close();
            main.mainStage.setScene(GameStoreCPH.neverInTheMain);

        });
        HBox buttonHBox = new HBox(20,cancelButton,buyButton);

        Region reg2 = new Region();
        reg2.setPrefWidth(300);
        totalLabel = new Label("Total price: ");
        valueLabel = new Label("  ");
        currencyLabel = new Label(" dkk");
        HBox labelHBox = new HBox(5,totalLabel,valueLabel,currencyLabel);

        VBox bottomVBox = new VBox(labelHBox,buttonHBox);
        HBox bottomHBox = new HBox(reg2,bottomVBox);

        //     ---------------------- UPDATE TOTAL PRICE ----------------------
        double totalPrice = 0;
        for(int i=0;i<GameStoreCPH.cartGameList.size();i++){
            totalPrice += GameStoreCPH.cartGameList.get(i).getPrice()*GameStoreCPH.cartGameList.get(i).getQty();
        }
        valueLabel.setText(String.valueOf(totalPrice));



        root.setTop(idHBox);
        root.setCenter(table);
        root.setBottom(bottomHBox);

        checkOutScene = new Scene(root, 500, 500);
        checkOutStage = new Stage();
        checkOutStage.setScene(checkOutScene);
        checkOutStage.setTitle("Check Out");
        checkOutStage.showAndWait();


    }


    public void finishTransaction(){


        // add new Cart to Database
        addNewCartToDB();
        addGamesByCartId();


        SelectCustomerReceipts setCustomerReceiptID = new SelectCustomerReceipts();

        ArrayList<Cart> carList = DBcart.readAllCarts();
        Debug.print("Cart List from Checkout "+ carList.get(carList.size()-1));
        GameStoreCPH main = new GameStoreCPH();
//        main.updateData();
        setCustomerReceiptID.setSelectedCart(carList.get(carList.size()-1));

        RecipetView r1 = new RecipetView();
        r1.reciept();






        // print receipt

        printReceipt(carList.get(carList.size()-1).getCart_id());




    }

    public void updateInventory(){
        Connection conn = null;
        Statement stmt = null;
        try{

            conn = Database.getConn();
            stmt = conn.createStatement();

            String sql ="";
            for(int i=0;i<GameStoreCPH.cartGameList.size();i++){
                sql = "update game set stock = "+(GameStoreCPH.cartGameList.get(i).getInventory()
                        - GameStoreCPH.cartGameList.get(i).getQty())+" where game_id = "+
                        GameStoreCPH.cartGameList.get(i).getGame_id();
                stmt.executeUpdate(sql);
                GameStoreCPH.cartGameList.get(i).setQty(0);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public static String printReceipt(int cartID){
        String receipt = "";
        receipt +=("\n");
        receipt +=("----------------------------------\n");
        receipt +=("            RoManiacs\n");
        receipt +=("----------------------------------\n");
        receipt +=("\n");

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = Database.getConn();
            stmt = conn.createStatement();

            String sql = "SELECT \n" +
                    "    g.name, gc.game_qty, g.price * gc.game_qty AS total_price\n" +
                    "FROM\n" +
                    "    game g\n" +
                    "        JOIN\n" +
                    "    games_by_card_id gc ON g.game_id = gc.game_id\n" +
                    "        JOIN\n" +
                    "    cart c ON gc.cart_id = c.cart_id\n" +
                    "        JOIN\n" +
                    "    customer cu ON cu.customer_id = c.customer_id\n" +
                    "WHERE\n" +
                    "    c.cart_id = (SELECT \n" +
                    "           cart_id\n" +
                    "        FROM\n" +
                    "            cart WHERE cart_id = "+cartID+")";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                int qty = rs.getInt("game_qty");
                double price = rs.getDouble("total_price");
                receipt+=(name+"\n\t"+qty+"\t\t"+price+" dkk \n");
                Debug.print(name+"\n\t"+qty+"\t\t"+price+" dkk");
            }
            Debug.print("");
            Debug.print("----------------------------------");

            sql = "SELECT \n" +
                    "    SUM(g.price * gc.game_qty) AS total\n" +
                    "FROM\n" +
                    "    game g\n" +
                    "        JOIN\n" +
                    "    games_by_card_id gc ON g.game_id = gc.game_id\n" +
                    "        JOIN\n" +
                    "    cart c ON gc.cart_id = c.cart_id\n" +
                    "        JOIN\n" +
                    "    customer cu ON cu.customer_id = c.customer_id\n" +
                    "WHERE\n" +
                    "    c.cart_id = (SELECT \n" +
                    "            MAX(cart_id)\n" +
                    "        FROM\n" +
                    "            cart)\n" +
                    "GROUP BY c.cart_id";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                double total = rs.getDouble("total");
                receipt+=("\n\n\nTotal price          "+total+" dkk");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return receipt;
    }



    public void addNewCartToDB(){
        Connection conn = null;
        Statement stmt = null;
        try{

            conn = Database.getConn();
            stmt = conn.createStatement();

            String sql ="insert into cart values (default,"+customerDialog.customerName+","
                    +l1.currentEmployee.getEmployee_id()+")";
            stmt.executeUpdate(sql);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addGamesByCartId(){
        Connection conn = null;
        Statement stmt = null;
        try{

            conn = Database.getConn();
            stmt = conn.createStatement();

            String sql = "select max(cart_id) from cart";
            ResultSet rs = stmt.executeQuery(sql);
            int cart_id = 0;
            while(rs.next()) {
                cart_id = rs.getInt("max(cart_id)");
            }
            try{
                conn = Database.getConn();
                stmt = conn.createStatement();

                for(int i=0;i<GameStoreCPH.cartGameList.size();i++) {
                    sql = "insert into games_by_card_id values ("+cart_id+","+GameStoreCPH.cartGameList.get(i).getGame_id()+
                            ","+GameStoreCPH.cartGameList.get(i).getQty()+")";
                    stmt.executeUpdate(sql);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
