package Gui.Transactions;

import Controller.ControllerApplication;
import Controller.ControllerMoneyPlaces;
import Controller.ControllerTransactions;
import Domain.MoneyPlace;
import Domain.Transaction;
import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import Gui.AlertGui.AlertController;
import Observer.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionsGuiController implements Observer  {

    private ControllerApplication controller;
    private ObservableList<Transaction> model;
    private ObservableList<MoneyPlace> modelMoneyPlace;
    private AlertController alert = new AlertController();

    @FXML
    private TableView<Transaction> table;

    @FXML
    private TableColumn<Transaction, String> columnType;

    @FXML
    private TableColumn<Transaction, String> columnAmount;

    @FXML
    private TableColumn<Transaction, String> columnFrom;

    @FXML
    private TableColumn<Transaction, LocalDate> columnDate;

    @FXML
    private TableColumn<Transaction, LocalTime> columnTime;

    @FXML
    private TableColumn<Transaction, String> columnName;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private ComboBox<MoneyPlace> comboBoxFrom;

    @FXML
    private DatePicker datePickerDate;

    @FXML
    private TextField textFieldTime;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private Label labelTotal;

    @FXML
    private TextField textFieldName;

    @FXML
    private ComboBox<MoneyPlace> comboBoxFromTop;

    @FXML
    private Label labelAmount;


    public void setController(ControllerApplication controller){
        this.controller = controller;
        model = FXCollections.observableArrayList(this.controller.getAllTransactions());
        modelMoneyPlace = FXCollections.observableArrayList(this.controller.getAllMoneyPlaces());


        controller.addObserverTransactionController(this);
        controller.addObserverMoneyPlace(this);

        setTableData();
        loadComboBox();
        setPromptTextForTextFields();
        loadTotal();

        comboBoxFromTopChanged(comboBoxFromTop.getSelectionModel().getSelectedItem());
    }

    private void loadTotal(){
        labelTotal.setText(controller.getTotal().toString() + " lei");
    }

    private void setPromptTextForTextFields(){
      textFieldTime.setPromptText("HH:MM:SS");
      datePickerDate.setPromptText("DD-MM-YYYY");
    }

    private void loadComboBox(){
        ObservableList<String> types;
        types = FXCollections.observableArrayList(
                "income",
                "outcome"
        );
        comboBoxType.setItems(types);
        comboBoxFrom.setItems(modelMoneyPlace);
        comboBoxFromTop.setItems(modelMoneyPlace);

        comboBoxFromTop.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->comboBoxFromTopChanged(newValue));

        selectFirstComboBoxes();
    }

    private void selectFirstComboBoxes(){
        comboBoxType.getSelectionModel().selectFirst();
        comboBoxFrom.getSelectionModel().selectFirst();
        comboBoxFromTop.getSelectionModel().selectFirst();
    }

    private void comboBoxFromTopChanged(MoneyPlace newValue) {
        if (null != newValue){
            labelAmount.setText(newValue.getAmount().toString() + " lei");
        }else{
            labelAmount.setText("0.0 lei");
        }
    }

    private void setTableData(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnFrom.setCellValueFactory((x)->new SimpleStringProperty(controller.getControllerMoneyPlaces().getMoneyPlaceName(x.getValue().getIdMoneyPlace())));
        table.setItems(model);
        table.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->tableViewChanged(newValue));
    }

    private void tableViewChanged(Transaction transaction){
        if (null != transaction){
            textAreaDescription.setText(transaction.getDescription());
            textFieldAmount.setText(transaction.getAmount().toString());
            textFieldTime.setText(transaction.getTime().toString());
            comboBoxType.getSelectionModel().select(transaction.getType());
            comboBoxFrom.getSelectionModel().select(controller.getControllerMoneyPlaces().getMoneyPlace(transaction.getIdMoneyPlace()));
            datePickerDate.setValue(transaction.getDate());
            textFieldName.setText(transaction.getName());
        }
    }

    private void loadModel(){
        model.setAll(controller.getAllTransactions());
    }

    private void loadModelMoneyPlace(){
        modelMoneyPlace.setAll(controller.getAllMoneyPlaces());
    }

    private void clearFields(){
        table.getSelectionModel().clearSelection();
        selectFirstComboBoxes();
        textFieldAmount.clear();
        textFieldTime.clear();
        textAreaDescription.clear();
        textFieldName.clear();
        datePickerDate.getEditor().clear();
    }

    @FXML
    void add(ActionEvent event) {
        if (comboBoxFrom.getSelectionModel().getSelectedItem() != null){
            if (datePickerDate.getValue() != null) {
                try {
                    Transaction t = controller.addTransaction(comboBoxFrom.getSelectionModel().getSelectedItem().getId(),
                            textFieldAmount.getText(),
                            textFieldName.getText(),
                            comboBoxType.getSelectionModel().getSelectedItem(),
                            textAreaDescription.getText(),
                            datePickerDate.getValue(),
                            textFieldTime.getText());
                    table.getSelectionModel().select(t);
                } catch (WrongInput | WrongInputTransaction wrongInput) {
                    alert.showAlert("Add transaction", "Add can not be done because:", wrongInput.getMessage());
                }
            }else{
                alert.showAlert("Add transaction", "Add can not be done because:","Invalid date!");
            }
        }else{
            alert.showAlert("Add transaction", "Add can not be done because:","There are no money places!");
        }
    }

    @FXML
    void clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void delete(ActionEvent event) {
        Transaction t = table.getSelectionModel().getSelectedItem();
        if (null == t){
            alert.showAlert("Delete transaction", "Delete can not be done because:","You didn't selected any transaction from the table!");
        }else{
            controller.deleteTransaction(t);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Transaction t = table.getSelectionModel().getSelectedItem();
        if (null == t){
            alert.showAlert("Update transaction", "Update can not be done because:","You didn't selected any transaction from the table!");
        }else{
            if (comboBoxFrom.getSelectionModel().getSelectedItem() != null) {
                try {
                    Transaction newT = Transaction.getTransaction(t.getId(),
                            comboBoxFrom.getSelectionModel().getSelectedItem().getId(),
                            textFieldAmount.getText(),
                            textFieldName.getText(),
                            comboBoxType.getSelectionModel().getSelectedItem(),
                            textAreaDescription.getText(),
                            datePickerDate.getValue(),
                            textFieldTime.getText());
                    controller.updateTransaction(t,newT);
                } catch (WrongInputTransaction wrongInputTransaction) {
                    alert.showAlert("Update transaction", "Update can not be done because:",wrongInputTransaction.getMessage());
                }
            }else{
                alert.showAlert("Update transaction", "Update can not be done because:","There is no money place!");
            }
        }
    }

    @Override
    public void update(Observable e) {
        if (e.getClass().equals(ControllerTransactions.class)){
            loadModel();
            loadTotal();
        }else if (e.getClass().equals(ControllerMoneyPlaces.class)){
            loadModelMoneyPlace();
            selectFirstComboBoxes();
        }
    }
}
