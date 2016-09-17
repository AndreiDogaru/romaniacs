package FrontRow.Database;

import FrontRow.Model.Cart;
import FrontRow.Model.Debug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Doggy on 26/04/2016.
 */
public class DBcart {


    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    public static ArrayList<Cart> readAllCarts(){

        ArrayList<Cart> cartList = new ArrayList<>();
        cartList.clear();
        try {
            String sql = "SELECT * FROM cart";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Cart c = new Cart();
                c.setCart_id(rs.getInt("cart_id"));
                c.setCustomer_id(rs.getInt("customer_id"));
                c.setEmployee_id(rs.getInt("staff_id"));
                cartList.add(c);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cartList;
    }

    public static void insertCart(Cart c){

        try{
            String sql = String.format("INSERT INTO cart VALUES(default,'%d','%d')", c.getCustomer_id(),c.getEmployee_id());

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        }catch (SQLException a){
            a.printStackTrace();
        }

        Debug.print("Cart entered "+ c);
    }

    public static ArrayList viewCartData(int customerId){
        ArrayList<Cart> customerCartData = new ArrayList<>();
        try{
            String sql = "SELECT \n" +
                    "  c.cart_id, SUM(g.price * gc.game_qty) AS total \n" +
                    "FROM\n" +
                    "   game g\n" +
                    "       JOIN\n" +
                    "   games_by_card_id gc ON g.game_id = gc.game_id\n" +
                    "       JOIN\n" +
                    "   cart c ON gc.cart_id = c.cart_id\n" +
                    "       JOIN\n" +
                    "   customer cu ON cu.customer_id = c.customer_id where c.customer_id = "+customerId+"\n" +
                    "GROUP BY c.cart_id";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Cart c = new Cart();
                 c.setCart_id(rs.getInt("cart_id"));
                 c.setCartTotal(rs.getDouble("total"));
                customerCartData.add(c);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return customerCartData;
    }
}
