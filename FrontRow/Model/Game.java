package FrontRow.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mac on 25/04/2016.
 */
public class Game {

    private SimpleDoubleProperty price;
    private SimpleStringProperty game_name;
    private SimpleIntegerProperty game_id;
    private SimpleStringProperty platform;
    private SimpleStringProperty type;
    private SimpleStringProperty release_date;
    private SimpleIntegerProperty age_rating;
    private SimpleStringProperty sys_req;
    private SimpleIntegerProperty inventory;
    private SimpleIntegerProperty qty;

    public Game(double price, String game_name, 
                int game_id, String platform, String type, String release_date, int age_rating, 
                String sys_req, int inventory) {
        this.price = new SimpleDoubleProperty(price);
        this.game_name = new SimpleStringProperty(game_name);
        this.game_id = new SimpleIntegerProperty(game_id);
        this.platform = new SimpleStringProperty(platform);
        this.type = new SimpleStringProperty(type);
        this.release_date = new SimpleStringProperty(release_date);
        this.age_rating = new SimpleIntegerProperty(age_rating);
        this.sys_req = new SimpleStringProperty(sys_req);
        this.inventory = new SimpleIntegerProperty(inventory);
        this.qty = new SimpleIntegerProperty(0);
    }

    public Game(){

    }

// SETTERS


    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public void setGame_name(String game_name) {
        this.game_name = new SimpleStringProperty(game_name);
    }

    public void setGame_id(int game_id) {
        this.game_id = new SimpleIntegerProperty(game_id);
    }

    public void setPlatform(String platform) {
        this.platform = new SimpleStringProperty(platform);
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public void setRelease_date(String release_date) {
        this.release_date = new SimpleStringProperty(release_date);
    }

    public void setAge_rating(int age_rating) {
        this.age_rating = new SimpleIntegerProperty(age_rating);
    }

    public void setSys_req(String sys_req) {
        this.sys_req = new SimpleStringProperty(sys_req);
    }

    public void setInventory(int inventory) {
        this.inventory = new SimpleIntegerProperty(inventory);
    }

    public void setQty(int qty) {
        this.qty = new SimpleIntegerProperty(qty);
    }

    //GETTERS

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public String getGame_name() {
        return game_name.get();
    }

    public SimpleStringProperty game_nameProperty() {
        return game_name;
    }

    public int getGame_id() {
        return game_id.get();
    }

    public SimpleIntegerProperty game_idProperty() {
        return game_id;
    }

    public String getPlatform() {
        return platform.get();
    }

    public SimpleStringProperty platformProperty() {
        return platform;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getRelease_date() {
        return release_date.get();
    }

    public SimpleStringProperty release_dateProperty() {
        return release_date;
    }

    public int getAge_rating() {
        return age_rating.get();
    }

    public SimpleIntegerProperty age_ratingProperty() {
        return age_rating;
    }

    public String getSys_req() {
        return sys_req.get();
    }

    public SimpleStringProperty sys_reqProperty() {
        return sys_req;
    }

    public int getInventory() {
        return inventory.get();
    }

    public SimpleIntegerProperty inventoryProperty() {
        return inventory;
    }

    public int getQty() {
        return qty.get();
    }

    public SimpleIntegerProperty qtyProperty() {
        return qty;
    }

    public String toString(){
        return String.format("Price: %s, Name: %s, Platform: %s, Type: %s,\n" +
                "Release date: %s, Age rating: %s, System requirements: %s, Inventory: %s"+"\n",
                priceProperty().getValue(), game_nameProperty().getValue(),
                platformProperty().getValue(),typeProperty().getValue(),release_dateProperty().getValue(),age_ratingProperty().getValue(),sys_reqProperty().getValue(),
        inventoryProperty().getValue());
    }
}
