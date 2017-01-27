package GuiControllers.MainWindow;

import Controller.ControllerApplication;
import GuiControllers.TransactionsToPdf.ControllerTransactionToPdf;
import GuiControllers.StartGui;
import Utils.WriteToLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        if (controllerApplication.getDataModified()) {
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Closing");
            alert.setHeaderText("Thanks for using wimm");
            alert.setContentText("Do you want to save?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");

            alert.getButtonTypes().addAll(buttonYes, buttonNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes) {
                controllerApplication.saveData();
            }
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
    private void saveAction(ActionEvent event) {
        if (controllerApplication.getDataModified()) {
                controllerApplication.saveData();
        }
    }

    @FXML
    private void saveTransactionsAsPdf(ActionEvent event) {
        Stage stageSavePdf = new Stage();
        Scene scene = new Scene(loadExportToPdfView(stageSavePdf));
        stageSavePdf.setScene(scene);
        stageSavePdf.setResizable(false);
        stageSavePdf.setTitle("Save transactions to pdf");
        stageSavePdf.show();
    }

    private Parent loadExportToPdfView(Stage stage){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartGui.class.getResource("/view/gui/fxml/TransactionToPdfView.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (Exception e) {
            WriteToLog.writeLog(e.getMessage());
        }
        ControllerTransactionToPdf controller = loader.getController();
        controller.setController(stage, controllerApplication);
        return parent;
    }

    @FXML
    void closeApplication(ActionEvent event) {
        saveData();
        primaryStage.close();
    }

}

