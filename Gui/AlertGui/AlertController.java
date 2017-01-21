package Gui.AlertGui;

import javafx.scene.control.Alert;

public class AlertController {
    private Alert alert;
    private boolean ok;

    public AlertController() {
        alert = new Alert(Alert.AlertType.ERROR);
        ok = true;
    }

    private void initialize(){
        if (ok){
            ok = false;

        }
    }
    public void showAlert(String name, String header, String text){
        initialize();
        alert.setTitle(name);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public Alert getAlert() {
        return alert;
    }
}
