
package FrontRow.views.dialogs;

import FrontRow.GameStoreCPH;
import FrontRow.Model.Debug;
import FrontRow.Model.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EditGameDialog {
    // TODO Create Dialog Window where you can View ALL game details
    private Button cancel, addToCart;
    private Stage gameStage = new Stage();
    private Game selectedGame;
    private GameStoreCPH main = new GameStoreCPH();

    private TextField qtyField = new TextField();

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void gameStage(Game g1){

        gameStage.setTitle("RoManIacs");

        selectedGame = new Game();
        selectedGame = g1;
        Label titel = new Label(g1.getGame_name());
        Label price = new Label(""+g1.getPrice());
        Label stock = new Label(""+g1.getInventory());
        Label genre = new Label(g1.getType());
        Label releaseDate = new Label(g1.getRelease_date());
        Label ageRating = new Label(""+g1.getAge_rating());
        Label sysReq = new Label(g1.getSys_req());


        Label titleLabel = new Label("Title");
        Label priceLabel = new Label("Price");
        Label stockLabel = new Label("Stock");
        Label genreLabel = new Label("Genre");
        Label releaseDateLabel = new Label("Release Date");
        Label ageRatingLabel = new Label("Age rating");
        Label sysReqLabel = new Label("System Requirements");
        Label qtyLabel = new Label("Qty");


        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        gridPane.addColumn(2, titel, price, stock, genre, releaseDate, ageRating, sysReq, qtyField);
        gridPane.addColumn(1, titleLabel, priceLabel, stockLabel, genreLabel, releaseDateLabel, ageRatingLabel, sysReqLabel, qtyLabel);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(30,30,30,30));
        borderPane.setRight(gridPane);

        ImageView gamePick = new ImageView();
        gamePick.setFitHeight(300);
        gamePick.setFitWidth(230);
        HBox hBox = new HBox(gamePick);
        hBox.setPadding(new Insets(30,30,30,30));
        Image warhammer = new Image(EditGameDialog.class.getResourceAsStream("/warhammer.jpg"));
        Image fifa = new Image(EditGameDialog.class.getResourceAsStream("/fifa.jpg"));
        Image fifa16 = new Image(EditGameDialog.class.getResourceAsStream("/fifa16.jpg"));
        Image wow = new Image(EditGameDialog.class.getResourceAsStream("/wow.jpg"));
        Image coh = new Image(EditGameDialog.class.getResourceAsStream("/coh.jpg"));
        Image xcom2 = new Image(EditGameDialog.class.getResourceAsStream("/warhammer.jpg"));
        Image heartstone = new Image(EditGameDialog.class.getResourceAsStream("/heartstone.jpg"));
        Image halo = new Image(EditGameDialog.class.getResourceAsStream("/halo.jpg"));
        Image diablo = new Image(EditGameDialog.class.getResourceAsStream("/diablo.jpg"));

        //sets immage == to game immage
        int id = g1.getGame_id();
        switch (id){
            case 1: gamePick.setImage(fifa16);
                break;
            case 2: gamePick.setImage(wow);
                break;
            case 3: gamePick.setImage(coh);
                break;

            case 4: gamePick.setImage(xcom2);
                break;
            case 5: gamePick.setImage(heartstone);
                break;
            case 6: gamePick.setImage(warhammer);
                break;

            case 7: gamePick.setImage(fifa);
                break;
            case 8: gamePick.setImage(halo);
                break;
            case 9: gamePick.setImage(diablo);
                break;
        }

        //buttons
        cancel = new Button("Cancel");
        addToCart = new Button("Add to cart");
        ButtonBar bBar = new ButtonBar();
        bBar.getButtons().addAll(cancel,addToCart);
        cancel.setAlignment(Pos.BOTTOM_RIGHT);
        addToCart.setAlignment(Pos.BOTTOM_RIGHT);
        bBar.setPadding(new Insets(20));
        addToCart.setDefaultButton(true);
        //add stuff to borderpane
        borderPane.setLeft(hBox);
        borderPane.setBottom(bBar);

        addListeners();

        Scene scene1 = new Scene(borderPane, 850,450);
        scene1.getStylesheets().add("FrontRow/views/CSS/playstationStyle.css");
        gameStage.setScene(scene1);
        gameStage.show();

    }

    private void addListeners(){
        cancel.setOnAction(event -> {
            gameStage.close();
        });

        addToCart.setOnAction(event -> {
            selectedGame.setQty(Integer.parseInt(qtyField.getText()));
            if (validateQty()) {
                main.cartGameList.add(selectedGame);
                Debug.print(selectedGame.getQty() + " " + selectedGame + " was added to Cart");
                gameStage.close();
            }


        });
    }

    private boolean validateQty(){
        if (selectedGame.getQty() > selectedGame.getInventory()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText("You entered an invalid quantity");
            alert.showAndWait();
            qtyField.clear();
            return false;
        }
        return true;
    }

}

