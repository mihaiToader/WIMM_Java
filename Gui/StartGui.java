package Gui;


import Controller.ControllerMoneyPlaces;
import Domain.MoneyPlace;
import Gui.MainWindow.MainWindowController;
import Gui.MoneyPlaces.MoneyPlaceGuiController;
import Repository.RepositorySerializable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class StartGui extends Application{

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(getMainWindowView(getMoneyPlaceLayout()), 700,600);

        //scene.getStylesheets().add(getClass().getResource("Theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("WIMM");
        primaryStage.show();
    }

    private Parent getMainWindowView(Parent moneyPlacesView){
        FXMLLoader loaderMain = new FXMLLoader();
        loaderMain.setLocation(StartGui.class.getResource("MainWindow/MainWindow.fxml"));
        Parent parentMain = null;
        try {
            parentMain = loaderMain.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainWindowController mainWindowController = loaderMain.getController();
        mainWindowController.setControllerViews(moneyPlacesView);

        return parentMain;
    }

    private Parent getMoneyPlaceLayout(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartGui.class.getResource("MoneyPlaces/MoneyPlacesView.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setControllerMoneyPlace(loader);

        return parent;
    }

    private void setControllerMoneyPlace(FXMLLoader loader){
        MoneyPlaceGuiController moneyPlaceGuiController = loader.getController();
        moneyPlaceGuiController.setControllerView();

        RepositorySerializable<MoneyPlace> repository = new RepositorySerializable<MoneyPlace>("src/Data/moneyPlace.bin");
        ControllerMoneyPlaces controllerMoneyPlaces = new ControllerMoneyPlaces(repository);
        moneyPlaceGuiController.setController(controllerMoneyPlaces);
    }

    public static void startGUI(String argv[]){
        launch(argv);
    }
}
