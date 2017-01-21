package Gui.MainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    @FXML
    private AnchorPane anchorPaneTabMoneyPlaces;

    @FXML
    private AnchorPane anchorPaneTransaction;

    public void setControllerViews(Node moneyPlacesView, Node transactionView){
        setMoneyPlacesView(moneyPlacesView);
        setTransactionView(transactionView);
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
        Platform.exit();
    }

}

