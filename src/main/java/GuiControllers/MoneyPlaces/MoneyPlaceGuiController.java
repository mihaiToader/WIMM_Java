package GuiControllers.MoneyPlaces;

import Controller.ControllerApplication;
import Domain.MoneyPlace;
import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import GuiControllers.AlertGui.AlertController;
import Observer.Observer;
import Observer.Observable;
import Validator.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class MoneyPlaceGuiController implements Observer{

    private ControllerApplication controller;
    private ObservableList<MoneyPlace> model;
    private AlertController alert = new AlertController();


    @FXML
    private TextField textFieldAmountTransfer;

    @FXML
    private GridPane gridPaneDetails;

    @FXML
    private TableView<MoneyPlace> table;

    @FXML
    private TableColumn<MoneyPlace, String> columnName;

    @FXML
    private TableColumn<MoneyPlace, String> columnAmount;

    @FXML
    private TextField textFiledName;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private TextArea textFieldDescription;

    @FXML
    private ComboBox<MoneyPlace> comboBoxFrom;

    @FXML
    private ComboBox<MoneyPlace> comboBoxTo;

    public void setController(ControllerApplication controller){
        this.controller = controller;
        this.controller.addObserverMoneyPlace(this);

        model = FXCollections.observableArrayList(controller.getAllMoneyPlaces());

        setTableData();
        initializeComboBoxes();
    }

    public void setControllerView(){
        ColumnConstraints column1 = new ColumnConstraints(100);
        column1.setHgrow(Priority.NEVER);
        gridPaneDetails.getColumnConstraints().set(0,column1);
    }

    private void initializeComboBoxes(){
        comboBoxFrom.setItems(model);
        comboBoxTo.setItems(model);
        selectFirstComboBoxes();
    }

    private void selectFirstComboBoxes(){
        comboBoxFrom.getSelectionModel().selectFirst();
        comboBoxTo.getSelectionModel().selectFirst();
    }

    private void setTableData(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        table.setItems(model);
        table.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->tableViewChanged(newValue));
    }

    private void tableViewChanged(MoneyPlace newValue) {
        if (null != newValue){
            textFiledName.setText(newValue.getName());
            textFieldAmount.setText(newValue.getAmount().toString());
            textFieldDescription.setText(newValue.getDescription());
        }
    }

    private void loadModel(){
        model.setAll(controller.getAllMoneyPlaces());
    }


    private void clearFields(){
        table.getSelectionModel().clearSelection();
        textFiledName.clear();
        textFieldAmount.clear();
        textFieldDescription.clear();
    }

    private String getAmountFromTextField(){
        if (textFieldAmount.getText().equals("")){
            return "0.0";
        }
        return textFieldAmount.getText();
    }

    @FXML
    void addMoneyPlace(ActionEvent event) {
        if (controller.getControllerMoneyPlaces().thisMoneyPlaceNameExists(textFiledName.getText())){
            alert.showAlert("Add money place", "Add ca not be done:", "Already exists a money place with the same name!");
        }else{
            try {
                MoneyPlace mP = MoneyPlace.getMoneyPlaceWithInvalidId(textFiledName.getText(),getAmountFromTextField(),textFieldDescription.getText());
                if (!mP.getAmount().equals(0.0)) {
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    LocalTime ltNow = LocalTime.of(lt.getHour(), lt.getMinute(), lt.getSecond());
                    String name = "Added money place " + mP.getName() + ".";
                    String type = "income";
                    String description = "";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Add");
                    alert.setHeaderText("If you add this money place will be added a transactions with:");
                    alert.setContentText("Type: " + type + "\n" +
                            "Amount: " + mP.getAmount().toString() + "\n" +
                            "From: " + mP.getName() + "\n" +
                            "Date: " + ld.toString() + "\n" +
                            "Time: " + ltNow.toString() + "\n" +
                            "Name: " + name + "\n" +
                            "Description: " + description + "\n" +
                            "\n\nDo you want to continue?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        MoneyPlace mPSaved = controller.getControllerMoneyPlaces().add(mP.getName(), mP.getAmount(), mP.getDescription());
                        controller.addTransaction(mPSaved.getId(), mPSaved.getAmount(), name, type, description, ld, ltNow);
                    }
                }else{
                    MoneyPlace mPSaved = controller.getControllerMoneyPlaces().add(mP.getName(), mP.getAmount(), mP.getDescription());
                    table.getSelectionModel().select(mPSaved);
                }
            } catch (WrongInputTransaction wrongInputTransaction) {
                alert.showAlert("Add money place", "Add ca not be done:", wrongInputTransaction.getMessage());
            }
        }
    }

    @FXML
    void clearMoneyPlace(ActionEvent event) {
        clearFields();
    }

    @FXML
    void deleteMoneyPlace(ActionEvent event) {
        MoneyPlace mP = table.getSelectionModel().getSelectedItem();
        if (null != mP){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete money place:");
            alert.setContentText("If you delete this money place all transactions related will be deleted too!\n" +
                    "Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                controller.deleteMoneyPlace(mP);
                clearFields();
            }
        }else{
            alert.showAlert("Delete money place", "Delete ca not be done:", "You have to select the money place you want to delete!");
        }
    }

    @FXML
    void updateMoneyPlace(ActionEvent event) {
        MoneyPlace oldMp = table.getSelectionModel().getSelectedItem();
        if (null != oldMp){
            try {
                MoneyPlace newMp = MoneyPlace.getMoneyPlaceWithInvalidId(textFiledName.getText(),getAmountFromTextField(),textFieldDescription.getText());
                newMp.setId(oldMp.getId());
                if (oldMp.getAmount().equals(newMp.getAmount())){
                    controller.getControllerMoneyPlaces().update(newMp);
                    controller.notifyAllObservers();
                }else{
                    String type;
                    Double amount;
                    String name = "Updated money place ";
                    String mPName;
                    if (oldMp.getAmount() > newMp.getAmount()){
                        type = "outcome";
                        amount = oldMp.getAmount() - newMp.getAmount();
                    }else{
                        type = "income";
                        amount = -oldMp.getAmount() + newMp.getAmount();
                    }
                    if (oldMp.getName().equals(newMp.getName())){
                        name += oldMp.getName() + ".";
                        mPName = oldMp.getName();
                    }else{
                        name += oldMp.getName() + " to " + newMp.getName() + ".";
                        mPName = newMp.getName();
                    }
                    LocalDate ld = LocalDate.now();
                    LocalTime lt = LocalTime.now();
                    LocalTime ltNow = LocalTime.of(lt.getHour(), lt.getMinute(), lt.getSecond());
                    String description = "Update!";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update");
                    alert.setHeaderText("If you update this money place will be added a transactions with:");
                    alert.setContentText("Type: " + type + "\n" +
                            "Amount: " + amount.toString() + "\n" +
                            "From: " + mPName + "\n" +
                            "Date: " + ld.toString() + "\n" +
                            "Time: " + ltNow.toString() + "\n" +
                            "Name: " + name + "\n" +
                            "Description: " + description + "\n" +
                            "\n\nDo you want to continue?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        controller.getControllerMoneyPlaces().update(newMp);
                        controller.addTransaction(newMp.getId(), amount, name, type, description, ld, ltNow);
                    }

                }
            } catch (WrongInputTransaction wrongInputTransaction) {
                alert.showAlert("Update money place", "Update ca not be done:", wrongInputTransaction.getMessage());
            }
        }else{
            alert.showAlert("Update money place", "Update ca not be done:", "You have to select the money place you want to update!");
        }
    }


    @FXML
    void transferMoney(ActionEvent event) {
        MoneyPlace from = comboBoxFrom.getSelectionModel().getSelectedItem();
        MoneyPlace to = comboBoxTo.getSelectionModel().getSelectedItem();

        try {
            Double amount = Validator.validateAmount(textFieldAmountTransfer.getText());
            if (from == null || to == null){
                alert.showAlert("Transfer money place", "Transfer ca not be done:", "There is no money place!");
            }else{
                if (from.equals(to)){
                    alert.showAlert("Transfer money place", "Transfer ca not be done:", "Can't transfer money to the same place!");
                }else{
                    if (amount > from.getAmount()){
                        alert.showAlert("Transfer money place", "Transfer ca not be done:", "Not enough money in " + from.getName() + " to transfer!");
                    }else {
                        String name = "Transfer from " + from.getName() + " to " + to.getName();
                        LocalTime now = LocalTime.now();
                        controller.addTransaction(from.getId(), amount.toString(), name, "outcome", "Transfer", LocalDate.now(), LocalTime.of(now.getHour(), now.getMinute(), now.getSecond()).toString());
                        controller.addTransaction(to.getId(), amount.toString(), name, "income", "Transfer", LocalDate.now(), LocalTime.of(now.getHour(), now.getMinute(), now.getSecond()).toString());
                    }
                }
            }

        } catch (WrongInput | WrongInputTransaction wrongInput) {
            alert.showAlert("Transfer money place", "Transfer ca not be done:", wrongInput.getMessage());
        }




    }

    @Override
    public void update(Observable e) {
        loadModel();
        selectFirstComboBoxes();
    }
}
