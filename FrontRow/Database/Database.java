package FrontRow.Database;

import FrontRow.Model.Debug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Mac on 25/04/2016.
 */
public class Database {
    private static Connection conn = null;

    public void connectDatabase(){
        Debug.print("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Debug.print("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        Debug.print("MySQL JDBC Driver Registered!");

        try {
            conn = DriverManager
                    .getConnection(Authentication.getDbUrl(), Authentication.getDbUser(), Authentication.getDbPass());
        } catch (SQLException e) {
            Debug.print("Connection Failed! Loading from XML Sheets? Or Try to Create the Tables from Here Later");
            e.printStackTrace();
            return;
        }
        if (conn != null) {
            Debug.print("Great Success! DB is Connected");
        } else {
            Debug.print("Failed to make connection!");
        }
    }
    public static Connection getConn() {
        return conn;
    }

}
