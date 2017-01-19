package Gui.MainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private AnchorPane anchorPaneTabMoneyPlaces;


    public void setControllerViews(Node moneyPlacesView){
        setMoneyPlacesView(moneyPlacesView);
    }

    private void setMoneyPlacesView(Node moneyPlacesView){
        anchorPaneTabMoneyPlaces.getChildren().add(moneyPlacesView);
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

