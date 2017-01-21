package Gui;


import Controller.ControllerApplication;
import Controller.ControllerMoneyPlaces;
import Controller.ControllerTransactions;
import Domain.MoneyPlace;
import Domain.Transaction;
import Gui.MainWindow.MainWindowController;
import Gui.MoneyPlaces.MoneyPlaceGuiController;
import Gui.Transactions.TransactionsGuiController;
import Repository.RepositorySerializable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;


public class StartGui extends Application{
    private ControllerApplication controller;

    @Override
    public void start(Stage primaryStage) {
        initializeControllerApplication();
        Scene scene = new Scene(getMainWindowView(), 700,600);

        //scene.getStylesheets().add(getClass().getResource("Theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("WIMM");
        primaryStage.show();
    }

    private void initializeControllerApplication(){
        RepositorySerializable<MoneyPlace> repositoryMP = new RepositorySerializable<MoneyPlace>("src/Data/moneyPlace.bin");
        ControllerMoneyPlaces controllerMoneyPlaces = new ControllerMoneyPlaces(repositoryMP);

        RepositorySerializable<Transaction> repositoryT = new RepositorySerializable<Transaction>("src/Data/transactions.bin");
        ControllerTransactions controllerTransactions = new ControllerTransactions(repositoryT);
        controller = new ControllerApplication(controllerMoneyPlaces,controllerTransactions);
    }

    private Parent getMainWindowView(){
        FXMLLoader loaderMain = new FXMLLoader();
        loaderMain.setLocation(StartGui.class.getResource("MainWindow/MainWindow.fxml"));
        Parent parentMain = null;
        try {
            parentMain = loaderMain.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMainWindowController(loaderMain);


        return parentMain;
    }

    private void setMainWindowController(FXMLLoader loaderMain){
        MainWindowController mainWindowController = loaderMain.getController();
        mainWindowController.setControllerViews(getMoneyPlaceLayout(), getTransactionLayout());
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


        moneyPlaceGuiController.setController(controller);
    }

    private Parent getTransactionLayout(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartGui.class.getResource("Transactions/TransactionsView.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setControllerTransactions(loader);
        return parent;

    }

    private void setControllerTransactions(FXMLLoader loader){
        TransactionsGuiController transactionsGuiController = loader.getController();

        transactionsGuiController.setController(controller);
    }

    public static void startGUI(String argv[]){
        launch(argv);
    }
}
