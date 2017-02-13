package GuiControllers.TransactionsToPdf;

import Controller.ControllerApplication;
import Domain.Transaction;
import Utils.Export.ExportToPdf.ExportTransactionsToPdf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ControllerTransactionToPdf {

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private CheckBox checkBoxWithMoneyPlaces;

    @FXML
    private CheckBox checkBoxWithTotal;

    private ControllerApplication controller;

    private FileChooser fileChooser;

    private Stage primaryStage;

    private ExportTransactionsToPdf exportPdf;

    public void setController(Stage primaryStage, ControllerApplication controller){
        this.controller = controller;

        this.primaryStage = primaryStage;

        exportPdf = new ExportTransactionsToPdf(controller);

        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    private String getPath() {
        File selectedFile =
                fileChooser.showSaveDialog(primaryStage);

        if(selectedFile == null){
            return "";
        }else{
            return selectedFile.getAbsolutePath();
        }
    }

    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Save can't be done because:");
        alert.setContentText(message);
        alert.show();
    }

    private void openFile(String path){
        File pdf = new File(path);
        if (Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(pdf);
            } catch (IOException e) {
                showError(e.getMessage());
            }
        }
    }

    private void exportTransactions(ArrayList<Transaction> transactions, String message){
        String path = getPath();
        if (!path.equals("")){
            try {
                exportPdf.createPdf(path, transactions,message,checkBoxWithMoneyPlaces.isSelected(),checkBoxWithTotal.isSelected());
                openFile(path);
            } catch (IOException e) {
                showError(e.getMessage());
            }
        }
    }

    @FXML
    private void saveAll(ActionEvent event) {
        exportTransactions(controller.getAllTransactions(), "All Transactions:\n");
        closeWindow();
    }

    @FXML
    private void saveInterval(ActionEvent event) {
        LocalDate from = datePickerFrom.getValue();
        LocalDate to = datePickerTo.getValue();
        if (from == null || to == null){
            showError("You didn't choose the interval!");
        }else{
            if (from.isAfter(to)){
                showError("It will be nice that first date would be smaller then second one :)");
            }else{
                exportTransactions(controller.getControllerTransactions().getTransactionsBetweenTwoDates(from,to),"Transactions between " + from.toString() + " to " + to.toString());
                closeWindow();
            }
        }
    }

    @FXML
    private void savePastMonth(ActionEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate firstOfThisMonth = today.withDayOfMonth( 1 );
        LocalDate firstOfLastMonth = firstOfThisMonth.minusMonths( 1 );
        LocalDate endOfLastMonth = firstOfThisMonth.minusDays( 1 );
        exportTransactions(controller.getControllerTransactions().getTransactionsBetweenTwoDates(firstOfLastMonth,endOfLastMonth),"Transactions from the month: " + firstOfLastMonth.getMonth()+ " " + firstOfLastMonth.getYear());
        closeWindow();
    }

    @FXML
    private void saveThisMonth(ActionEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate firstOfThisMonth = today.withDayOfMonth( 1 );
        LocalDate firstOfNextMonth = firstOfThisMonth.plusMonths( 1 );
        LocalDate endOfThisMonth = firstOfNextMonth.minusDays( 1 );
        exportTransactions(controller.getControllerTransactions().getTransactionsBetweenTwoDates(firstOfThisMonth,endOfThisMonth),"Transactions from the month: " + firstOfThisMonth.getMonth()+ " " + firstOfThisMonth.getYear());
        closeWindow();
    }

    private void closeWindow(){
        primaryStage.close();
    }
}
