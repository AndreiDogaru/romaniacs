package FrontRow.Database;

import FrontRow.Model.Debug;
import FrontRow.Model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mac on 25/04/2016.
 */
public class DBemployee {
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    public static ArrayList<Employee> readAllEmployees(){
        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.clear();
        try {
            String sql = "SELECT * FROM staff";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Employee e = new Employee();
                e.setEmployee_id(rs.getInt("staff_id"));
                e.setFirst_name(rs.getString("first_name"));
                e.setLast_name(rs.getString("last_name"));
                e.setPassword(rs.getString("password"));
                employeeList.add(e);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeeList;
    }

    public static void insertEmployee(Employee e){

        try{
            String sql = String.format("INSERT INTO staff VALUES(default,'%s','%s','%s')", e.getFirst_name(),e.getLast_name(),e.getPassword());

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        }catch (SQLException a){
            a.printStackTrace();
        }

        Debug.print("Employee entered "+ e);
    }
}
