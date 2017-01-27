package Utils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDateTime;

public class WriteToLog {

    public static String makeDir(){
        String pathToDocuments = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        String pathToData = pathToDocuments + "\\" + "WIMM";
        File data = new File(pathToData);
        if (!data.exists()){
            data.mkdir();
        }
        return pathToData;
    }
    public static void writeLog(String message){
        String fileName = makeDir() + "\\" + "log.txt";
        try
        {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
            message = LocalDateTime.now().toString() + " - " + message;
            writer.append(message);
            writer.newLine();
            writer.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
}
