package GuiControllers.MainWindow;

import Controller.ControllerApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

public class MainWindowController {

    @FXML
    private AnchorPane anchorPaneTabMoneyPlaces;

    @FXML
    private AnchorPane anchorPaneTransaction;

    private Stage primaryStage;

    private ControllerApplication controllerApplication;

    public void setMainController(Stage pS,ControllerApplication controllerApplication, Node moneyPlacesView, Node transactionView){
        setMoneyPlacesView(moneyPlacesView);
        setTransactionView(transactionView);
        primaryStage = pS;
        this.controllerApplication = controllerApplication;

        pS.setOnCloseRequest(x->saveData());
    }

    private void saveData(){
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("Closing");
        alert.setHeaderText("Thanks for using wimm");
        alert.setContentText("Do you want to save?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().addAll(buttonYes,buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes) {
            controllerApplication.saveData();
        }
    }

    private void setMoneyPlacesView(Node moneyPlacesView){
        anchorPaneTabMoneyPlaces.getChildren().add(moneyPlacesView);
        setAnchorPane(moneyPlacesView);
    }

    private void setTransactionView(Node transactionView){
        anchorPaneTransaction.getChildren().add(transactionView);
        setAnchorPane(transactionView);
    }

    private void  setAnchorPane(Node moneyPlacesView){
        AnchorPane.setTopAnchor(moneyPlacesView, 0.0);
        AnchorPane.setBottomAnchor(moneyPlacesView, 0.0);
        AnchorPane.setLeftAnchor(moneyPlacesView, 0.0);
        AnchorPane.setRightAnchor(moneyPlacesView, 0.0);
    }

    @FXML
    void closeApplication(ActionEvent event) {
        saveData();
        primaryStage.close();
    }

}

