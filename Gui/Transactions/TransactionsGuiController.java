package Gui.Transactions;

import Domain.MoneyPlace;
import Domain.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionsGuiController {
    @FXML
    private TableView<Transaction> table;

    @FXML
    private TableColumn<Transaction, String> columnType;

    @FXML
    private TableColumn<Transaction, String> columnAmount;

    @FXML
    private TableColumn<Transaction, String> columnFrom;

    @FXML
    private TableColumn<Transaction, String> columnDate;

    @FXML
    private TableColumn<Transaction, String> columnTime;

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
    void add(ActionEvent event) {

    }

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }
}
