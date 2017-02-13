package Utils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

public class CreateDataFiles {
    private String pathToTransactions;
    private String pathToMoneyPlace;
    private String pathToData;
    private Boolean hmmmmm = true;

    public CreateDataFiles() {
        String pathToDocuments = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        if (hmmmmm){
            pathToData = pathToDocuments + "\\" + "WIMM_develop";
        }else{
            pathToData = pathToDocuments + "\\" + "WIMM";
        }
        File data = new File(pathToData);
        if (!data.exists()){
            data.mkdir();
        }
        pathToTransactions = pathToData + "\\" + "transactions.bin";
        pathToMoneyPlace = pathToData + "\\" + "moneyPlaces.bin";
    }

    public String getPathToTransactions() throws IOException {
        File transactionFile = new File(pathToTransactions);
        if (!transactionFile.exists()){
            transactionFile.createNewFile();
        }
        return pathToTransactions;
    }

    public String getPathToMoneyPlace() throws IOException {
        File moneyPlaceFile = new File(pathToMoneyPlace);
        if (!moneyPlaceFile.exists()){
            moneyPlaceFile.createNewFile();
        }
        return pathToMoneyPlace;
    }

    public String getPathToDataStore() throws IOException {
        String pathToDataStore = pathToData + "\\" + "data.bin";
        File dataFile = new File(pathToDataStore);
        if (!dataFile.exists()){
            dataFile.createNewFile();
        }
        return pathToDataStore;
    }


}
