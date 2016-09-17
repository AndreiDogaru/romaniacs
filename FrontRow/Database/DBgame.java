package FrontRow.Database;

import FrontRow.Model.Debug;
import FrontRow.Model.Game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mac on 25/04/2016.
 */
public class DBgame {
    private static Connection conn = Database.getConn();
    private static Statement stm = null;

    public static ArrayList<Game> readAllGames() {
        ArrayList<Game> games = new ArrayList<>();
        games.clear();
        try {
            String sql = "SELECT * FROM game";

            stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(sql);

             while (rs.next()){
                 Game g1 = new Game();
                 g1.setPrice(rs.getDouble("price"));
                 g1.setGame_name(rs.getString("name"));
                 g1.setGame_id(rs.getInt("game_id"));
                 g1.setRelease_date(rs.getString("release_date"));
                 g1.setInventory(rs.getInt("stock"));
                 g1.setPlatform(rs.getString("platform"));
                 g1.setAge_rating(rs.getInt("age_rating"));
                 g1.setSys_req(rs.getString("system_requirements"));
                 g1.setType(rs.getString("type"));
                 games.add(g1);

             }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return games;
    }
    public static void insertGame(Game g){
        try {
            String sql = String.format("INSERT INTO game VALUES(default, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',)",
                    g.getGame_id(), g.getGame_name(), g.getPrice(), g.getType(), g.getPlatform(), g.getRelease_date(),g.getAge_rating(), g.getInventory(),g.getSys_req());
            stm = conn.createStatement();
            stm.executeUpdate(sql);

            Debug.print("Game Successfully Entered"+g);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
