package FrontRow.views.dialogs;

import FrontRow.Model.Debug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import FrontRow.Database.DBgame;
import FrontRow.Model.Game;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by SaxoM on 27/04/16.
 */
public class GameTable {

    private Button add;
    private TextField TFname, TFprice, TFageRating, TFstock, TFsysReq, searchField;
    private DatePicker releaseDate;
    private ChoiceBox<String> sysReq, type, platform;
    private TableView<Game> gameTable;
    private TableColumn<Game, Integer> idColumn, ageRatingColumn, sysRequirementsColumn, inventoryColumn;
    private TableColumn<Game, String> gameNameColumn, platformColumn, typeColumn, releaseDateColumn;
    private TableColumn<Game, Double> priceColumn;


    private ObservableList<Game> listO;

    public BorderPane showGameTableView(){
        BorderPane bp = new BorderPane();
        searchField = new TextField();
        searchField.setStyle("-fx-text-fill: lawngreen;\n" +
                "    -fx-background-color: black;");
        searchField.promptTextProperty().setValue("Search by Name, Type, or Platform");
        bp.setTop(searchField);
        bp.setCenter(createGameTable());


        searchFieldListeners();

        return bp;
    }

    private TableView<Game> createGameTable(){
        gameTable = new TableView<>();
        listO = FXCollections.observableArrayList(DBgame.readAllGames());

        idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("game_id"));

        gameNameColumn = new TableColumn<>("Name");
        gameNameColumn.setMinWidth(150);
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("game_name"));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(150);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        platformColumn = new TableColumn<>("Platform");
        platformColumn.setMinWidth(150);
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));

        typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setMinWidth(150);
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("release_date"));

        ageRatingColumn = new TableColumn<>("Age Rating");
        ageRatingColumn.setMinWidth(150);
        ageRatingColumn.setCellValueFactory(new PropertyValueFactory<>("age_rating"));

        sysRequirementsColumn = new TableColumn<>("Sys Req");
        sysRequirementsColumn.setMinWidth(250);
        sysRequirementsColumn.setCellValueFactory(new PropertyValueFactory<>("sys_req"));

       inventoryColumn = new TableColumn<>("* In Stock");
        inventoryColumn.setMinWidth(100);
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventory"));

        gameTable.getColumns().addAll(idColumn, gameNameColumn, priceColumn, platformColumn, typeColumn, releaseDateColumn, ageRatingColumn, sysRequirementsColumn, inventoryColumn);
        gameTable.setItems(listO);

        gameTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



        gameTable.setRowFactory( tv -> {
            TableRow<Game> row = new TableRow<>();
            gameTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Game g1 = new Game();
                    g1 = tv.getSelectionModel().getSelectedItem();
                    EditGameDialog e1 = new EditGameDialog();
                    e1.gameStage(g1);
                }
            });
            return row;
        });

        gameTable.getStylesheets().add("FrontRow/views/CSS/playstationStyle.css");
        return gameTable;
    }

    public VBox setBottom(){
        HBox addUserRow = new HBox();
        //------------------------------fields
        TFname = new TextField();
        TFprice=new TextField();
        TFageRating = new TextField();
        TFstock = new TextField();

        sysReq = new ChoiceBox();
        sysReq.getItems().addAll("low", "medium", " high");
        type = new ChoiceBox();
        type.getItems().addAll("sport", "shooter", "rpg", "adventure");
        platform = new ChoiceBox();
        platform.getItems().addAll("pc", "ps", "xbox");

        releaseDate = new DatePicker();
        //--------------------------------



        add=new Button("Add game");
        add.setOnAction(event -> insertGame());


        addUserRow.getChildren().addAll(add,TFname, TFprice, platform, type,releaseDate ,TFageRating,sysReq, TFstock );

        VBox vBox = new VBox(addUserRow);

        return vBox;
    }

    private void insertGame(){
        Game g1 = new Game();
        g1.setGame_name(TFname.getText());

        sysReq.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            g1.setSys_req(newValue);
            Debug.print(newValue + "  was selected");
        });

        type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            g1.setType(newValue);
            Debug.print(newValue + "  was selected");
        });

        platform.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            g1.setPlatform(newValue);
            Debug.print(newValue + "  was selected");
        });

        g1.setRelease_date(releaseDate.getValue().toString());

        DBgame.insertGame(g1);

        listO.addAll(g1);

    }

    private  void addListeners(){

    }

    private void searchFieldListeners() {
        // 0. Initialize the columns.
        gameNameColumn.setCellValueFactory(cellData -> cellData.getValue().game_nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        platformColumn.setCellValueFactory(cellData -> cellData.getValue().platformProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Game> filteredData = new FilteredList<>(listO, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(game -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (game.getGame_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches game name.
                } else if (game.getPlatform().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches platform type.
                } else if (game.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;  // Filter matches game type
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Game> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(gameTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        gameTable.setItems(sortedData);
    }
}
