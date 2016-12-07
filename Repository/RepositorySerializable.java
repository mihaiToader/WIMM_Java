package Repository;

import Domain.HasId;
import Domain.SerializableWithId;

import java.io.*;
import java.util.ArrayList;

public class RepositorySerializable<T extends SerializableWithId> extends Repository<T> {
    private String fileName;


    public RepositorySerializable(String fileName){
        this.fileName = fileName;
        loadData();

    }

    @SuppressWarnings("unchecked")
    public void loadData()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            if (br.readLine() != null) {
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream input = new ObjectInputStream(fis);
                all = (ArrayList<T>) input.readObject();
                input.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void saveData()
    {
        try
        {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
            output.writeObject(all);
            output.close();
        }
        catch (IOException ex)
        {
           all.clear();
        }
    }




}
