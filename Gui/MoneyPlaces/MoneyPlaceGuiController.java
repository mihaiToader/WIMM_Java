package Gui.MoneyPlaces;

import Controller.ControllerMoneyPlaces;
import Domain.MoneyPlace;
import Exceptions.WrongInputTransaction;
import Gui.AlertGui.AlertController;
import Observer.Observer;
import Observer.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class MoneyPlaceGuiController implements Observer{

    private ControllerMoneyPlaces controller;
    private ObservableList<MoneyPlace> model;
    private AlertController alert = new AlertController();

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

    public void setController(ControllerMoneyPlaces controller){
        this.controller = controller;
        this.controller.addObserver(this);

        model = FXCollections.observableArrayList(controller.getAll());

        setTableData();
    }

    public void setControllerView(){
        ColumnConstraints column1 = new ColumnConstraints(100);
        column1.setHgrow(Priority.NEVER);
        gridPaneDetails.getColumnConstraints().set(0,column1);
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

    private void loadTable(){
        model.setAll(controller.getAll());
    }


    private void clearFields(){
        textFiledName.clear();
        textFieldAmount.clear();
        textFieldDescription.clear();
    }

    @FXML
    void addMoneyPlace(ActionEvent event) {
        try {
            controller.add(
                    textFiledName.getText(),
                    textFieldAmount.getText(),
                    textFieldDescription.getText()
            );
        } catch (WrongInputTransaction wrongInputTransaction) {
            alert.showAlert("Add money place", "Add can not be done because:",wrongInputTransaction.getMessage());
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
            controller.delete(mP.getId());
            table.getSelectionModel().select(0);
        }else{
            alert.showAlert("Delete money place", "Delete ca not be done:", "You have to select the money place you want to delete!");
        }
    }

    @FXML
    void updateMoneyPlace(ActionEvent event) {

    }

    @Override
    public void update(Observable e) {
        loadTable();
    }
}
