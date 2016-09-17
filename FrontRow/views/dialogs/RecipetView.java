package FrontRow.views.dialogs;

import FrontRow.GameStoreCPH;
import FrontRow.views.CheckOutView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by SaxoM on 28/04/16.
 */
public class RecipetView {
    public BorderPane borderPane;
    public TextArea textArea;
    public Stage stage;
    public Button ok, print;

    public void reciept(){
        GameStoreCPH main = new GameStoreCPH();
//        main.updateData();
        borderPane = new BorderPane();
        textArea = new TextArea();
        textArea.setMaxSize(500,500);
        textArea.setText(CheckOutView.printReceipt(SelectCustomerReceipts.getSelectedCart().getCart_id()));
        borderPane.setCenter(textArea);
        Scene s1 = new Scene(borderPane,320,540);
        textArea.setStyle("-fx-text-alignment: center");


        ok = new Button("OK");
        print = new Button("Print Receipt");

        textArea.setEditable(false);
        
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(20));
        buttonBar.getButtons().addAll(ok, print);
        ok.setAlignment(Pos.BOTTOM_RIGHT);
        print.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(buttonBar);
        ok.setOnAction(event -> stage.close());
        stage = new Stage();
        stage.setScene(s1);
        stage.showAndWait();
    }
}
