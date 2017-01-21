package Gui.TransactionsSmallWindow;

import javafx.fxml.FXML;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;

public class  TransactionsSmallWindowController {

    @FXML
    private ComboBox<?> comboBoxType;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private ComboBox<?> comboBoxFrom;

    @FXML
    private DatePicker datePickerDate;

    @FXML
    private TextField textFieldTime;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaDescription;

}
