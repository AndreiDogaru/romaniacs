package FrontRow.views.menus;

import FrontRow.GameStoreCPH;
import FrontRow.views.dialogs.AddSelectCustomerDialog;
import FrontRow.views.dialogs.LogInDialog;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.stage.Stage;

/**
 * Created by chill on 4/25/16.
 */
public class MainMenuBar {

    // Still Buggy, does not close all of the windows..

    private Stage activeStage;

    public void setActiveStage(Stage activeStage) {
        this.activeStage = activeStage;
    }

    public Stage getActiveStage() {
        return activeStage;
    }

    // TODO - create main menu Bar here

    public MenuBar mainMenuBar(){
        activeStage = new Stage();
        MenuBar mb = new MenuBar();
        Menu file = new Menu("File");
        MenuItem logout = new MenuItem("Logout");
        MenuItem exit = new MenuItem("Exit");



        Menu view = new Menu("View");
        //MenuItem viewCustomers = new MenuItem("View Customers");
        //MenuItem viewGames = new MenuItem("View Games");

        file.getItems().addAll(logout, exit);
        //view.getItems().addAll(viewCustomers, viewGames);
        mb.getMenus().addAll(file, view);

        // Listeners //
        logout.setOnAction(event -> {

            GameStoreCPH main = new GameStoreCPH();
            activeStage = new Stage();
                activeStage=    main.getMainStage();
            activeStage.close();





            activeStage.close();

            LogInDialog logInDialog = new LogInDialog();
            logInDialog.viewLogInScreen();

        });

        exit.setOnAction(event -> {
            GameStoreCPH main = new GameStoreCPH();
            activeStage=    main.getMainStage();
            activeStage.close();
        });

        return mb;
    }

}
