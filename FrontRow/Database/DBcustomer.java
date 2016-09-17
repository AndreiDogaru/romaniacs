package FrontRow.Database;

import FrontRow.Model.Customer;
import FrontRow.Model.Debug;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Chill on 25/04/2016.
 */
public class DBcustomer {


    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    public static ArrayList<Customer> readAllCustomers(){
        ArrayList<Customer> customersList = new ArrayList<>();
        customersList.clear();
        try{
            String sql = "Select * FROM customer";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Customer c = new Customer();
                c.setCustomer_id(rs.getInt("customer_id"));
                c.setFirst_name(rs.getString("first_name"));
                c.setLast_name(rs.getString("last_name"));
                customersList.add(c);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return customersList;
    }

    public static void insertUpdateCustomer(int id, Customer c){
        try {
            String sql;
            if (id == 0) {
               sql =  String.format("INSERT INTO CUSTOMER VALUES(default, '%s', '%s')"
                        , c.getFirst_name(), c.getLast_name());
            } else {
                sql =  String.format("INSERT INTO CUSTOMER VALUES(%s, '%s', '%s')",
                        c.getCustomer_id(), c.getFirst_name(), c.getLast_name());
            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            Debug.print("Customer Successfully Entered"+ c);

        } catch (SQLException e){
            e.printStackTrace();

        }
    }

}
